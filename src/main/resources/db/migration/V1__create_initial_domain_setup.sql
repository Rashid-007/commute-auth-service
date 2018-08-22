create table commute_account_credentials(
  id bigserial not null,
  username VARCHAR(255) not null,
  password_hash VARCHAR(60) not null,
  primary key (id)
);

create table commute_account_role (
  name VARCHAR(50) not null,
  primary key (name)
);


create table commute_account (
  id bigserial not null,
  account_credentials_id int8,
  account_expired boolean not null,
  account_locked boolean not null,
  credentials_expired boolean not null,
  creation_date TIMESTAMP NOT NULL DEFAULT now(),
  created_by VARCHAR(255) NOT NULL DEFAULT 'anonymous',
  last_updated_by VARCHAR(255),
  last_updated_date TIMESTAMP,
  last_login_date TIMESTAMP,
  enabled boolean not null,
  email_verified boolean,
  primary key (id)
);

create table commute_account_account_role(
  account_id int8 not null,
  role_name VARCHAR(50),
  primary key (account_id, role_name)
);



