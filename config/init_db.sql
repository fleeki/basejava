create table resume
(
  uuid      char(36) not null
    constraint resume_pk primary key,
  full_name text     not null
);

alter table resume
  owner to "Pavel";

create table contact
(
  id          serial   not null
    constraint contact_pk primary key,
  type        text     not null,
  value       text     not null,
  resume_uuid char(36) not null
    constraint contact_resume_uuid_fk
      references resume on update restrict on delete cascade
);

alter table contact
  owner to "Pavel";

create unique index contact_uuid_type_index on contact (resume_uuid, type);

create or replace function resumes_count(out cnt integer) as
$$
begin
  select count(*)
  from resume into cnt;
  return;
end;
$$
  language plpgsql;