package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.<Void>execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.<Void>transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }

            insertContacts(connection, resume);
            insertSections(connection, resume);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.<Void>transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE resume r SET full_name = ? WHERE r.uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }

            doDelete(connection, resume);
            insertContacts(connection, resume);
            insertSections(connection, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(connection -> {
            Resume resume;
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                } else {
                    resume = new Resume(uuid, rs.getString("full_name"));
                }
            }

            getContacts(connection, resume, uuid);
            getSections(connection, resume, uuid);
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>execute("DELETE FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            List<Resume> list = new ArrayList<>();
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM resume ORDER BY full_name, uuid");
                while (rs.next()) {
                    String uuid = rs.getString("uuid").trim();
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    getContacts(connection, resume, uuid);
                    getSections(connection, resume, uuid);
                    list.add(resume);
                }
            }
            return list;
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void doDelete(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("" +
                "DELETE FROM contact c WHERE c.resume_uuid = ?;" +
                "DELETE FROM section s WHERE s.resume_uuid = ?")) {
            String uuid = resume.getUuid();
            ps.setString(1, uuid);
            ps.setString(2, uuid);
            ps.execute();
        }
    }

    private void insertContacts(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO section (resume_uuid, title, item) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                SectionType sectionType = entry.getKey();
                ps.setString(1, resume.getUuid());
                ps.setString(2, sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        ps.setString(3, ((TextSection) entry.getValue()).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        StringBuilder sb = new StringBuilder();
                        for (String item : ((ListSection) entry.getValue()).getItems()) {
                            sb.append("\n" + item);
                        }
                        ps.setString(3, sb.toString());
                        break;
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(ResultSet rs, Resume resume) throws SQLException {
        String title = rs.getString("title");
        if (title != null) {
            SectionType type = SectionType.valueOf(title);
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    resume.addSection(type, new TextSection(rs.getString("item")));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    resume.addSection(type, new ListSection(rs.getString("item").trim().split("\n")));
                    break;
            }
        }
    }

    private void getContacts(Connection connection, Resume resume, String uuid) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT c.type, c.value FROM contact c WHERE c.resume_uuid = ?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addContact(rs, resume);
            }
        }
    }

    private void getSections(Connection connection, Resume resume, String uuid) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT s.title, s.item FROM section s WHERE s.resume_uuid = ?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addSection(rs, resume);
            }
        }
    }
}