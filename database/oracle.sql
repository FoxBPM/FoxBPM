
------------------------------------------------------
-- Export file for user FOXBPM                      --
-- Created by Administrator on 2014/12/29, 11:29:48 --
------------------------------------------------------


create table AU_GROUP_RELATION
(
  guid      VARCHAR2(64) not null,
  userid    VARCHAR2(512),
  groupid   VARCHAR2(512),
  grouptype VARCHAR2(512)
)
;
alter table AU_GROUP_RELATION
  add primary key (GUID);


create table AU_ORGINFO
(
  orgid    VARCHAR2(64) not null,
  suporgid VARCHAR2(512),
  orgname  VARCHAR2(512)
)
;
alter table AU_ORGINFO
  add primary key (ORGID);


create table AU_ROLEINFO
(
  roleid   VARCHAR2(64) not null,
  rolename VARCHAR2(512)
)
;
alter table AU_ROLEINFO
  add primary key (ROLEID);


create table AU_USERINFO
(
  userid   VARCHAR2(64) not null,
  username VARCHAR2(512),
  password VARCHAR2(512),
  email    VARCHAR2(250),
  tel      VARCHAR2(255),
  image    VARCHAR2(64)
)
;
alter table AU_USERINFO
  add primary key (USERID);


create table FOXBPM_AGENT
(
  id         VARCHAR2(255) not null,
  agent_user VARCHAR2(255),
  starttime  TIMESTAMP(6),
  endtime    TIMESTAMP(6),
  status     VARCHAR2(255)
)
;
alter table FOXBPM_AGENT
  add primary key (ID);


create table FOXBPM_AGENT_DETAILS
(
  id                    VARCHAR2(255) not null,
  agent_id              VARCHAR2(255),
  processdefinition_key VARCHAR2(255),
  agent_touser          VARCHAR2(255)
)
;
alter table FOXBPM_AGENT_DETAILS
  add primary key (ID);


create table FOXBPM_DEF_BYTEARRAY
(
  id            VARCHAR2(64) not null,
  rev           NUMBER(12),
  name          VARCHAR2(512),
  bytes         BLOB,
  deployment_id VARCHAR2(256)
)
;
alter table FOXBPM_DEF_BYTEARRAY
  add primary key (ID);


create table FOXBPM_DEF_DEPLOYMENT
(
  id          VARCHAR2(64) not null,
  name        VARCHAR2(512),
  deploy_time TIMESTAMP(6)
)
;
alter table FOXBPM_DEF_DEPLOYMENT
  add primary key (ID);


create table FOXBPM_DEF_PROCESSDEFINITION
(
  difinitions_key       VARCHAR2(512),
  difinitions_id        VARCHAR2(512),
  process_key           VARCHAR2(512),
  process_id            VARCHAR2(250) not null,
  category              VARCHAR2(255),
  process_name          VARCHAR2(255),
  version               INTEGER,
  resource_name         VARCHAR2(4000),
  deployment_id         VARCHAR2(64),
  diagram_resource_name VARCHAR2(512),
  start_form_key        VARCHAR2(1024),
  resource_id           VARCHAR2(64),
  sub_task_id           VARCHAR2(128),
  rev_                  INTEGER default 0
)
;
alter table FOXBPM_DEF_PROCESSDEFINITION
  add primary key (PROCESS_ID);


create table FOXBPM_MAIL
(
  mail_id        VARCHAR2(128) not null,
  mail_name      VARCHAR2(4000),
  mail_to        VARCHAR2(4000),
  mail_subject   VARCHAR2(4000),
  mail_body      BLOB,
  mail_status    VARCHAR2(64),
  create_time    TIMESTAMP(6),
  send_time      TIMESTAMP(6),
  mail_cc        VARCHAR2(4000),
  create_user    VARCHAR2(64),
  failure_reason VARCHAR2(4000)
)
;
alter table FOXBPM_MAIL
  add primary key (MAIL_ID);

create table FOXBPM_RUN_PROCESSINSTANCE
(
  id                    VARCHAR2(64) not null,
  processdefinition_id  VARCHAR2(512),
  processdefinition_key VARCHAR2(512),
  subject               VARCHAR2(4000),
  start_time            TIMESTAMP(6),
  end_time              TIMESTAMP(6),
  definition_id         VARCHAR2(64),
  roottoken_id          VARCHAR2(64),
  biz_key               VARCHAR2(64),
  initiator             VARCHAR2(64),
  start_author          VARCHAR2(64),
  issuspended           VARCHAR2(20),
  parent_id             VARCHAR2(64),
  parent_token_id       VARCHAR2(64),
  update_time           TIMESTAMP(6),
  processlocation       VARCHAR2(2048),
  instance_status       VARCHAR2(45),
  archive_time          TIMESTAMP(6),
  rev_                  INTEGER default 0
)
;
alter table FOXBPM_RUN_PROCESSINSTANCE
  add primary key (ID);


create table FOXBPM_RUN_PROCESS_INFO
(
  processinstanceid VARCHAR2(64),
  update_time       VARCHAR2(64),
  process_status    VARCHAR2(64),
  process_step      VARCHAR2(256),
  initator         VARCHAR2(64),
  bizkey            VARCHAR2(64)
)
;

create table FOXBPM_RUN_RUNNINGTRACK
(
  id                    VARCHAR2(64) not null,
  processinstance_id    VARCHAR2(64),
  processdefinition_id  VARCHAR2(512),
  processdefinition_key VARCHAR2(512),
  token_id              VARCHAR2(64),
  parent_token_id       VARCHAR2(64),
  execution_time        TIMESTAMP(6),
  track_record          VARCHAR2(64),
  operator              VARCHAR2(512),
  node_id               VARCHAR2(128),
  node_name             VARCHAR2(512),
  event_name            VARCHAR2(512),
  archive_time          TIMESTAMP(6)
)
;
alter table FOXBPM_RUN_RUNNINGTRACK
  add primary key (ID);

create table FOXBPM_RUN_TASK
(
  id                       VARCHAR2(64) not null,
  processinstance_id       VARCHAR2(64),
  processdefinition_id     VARCHAR2(512),
  version                  INTEGER,
  token_id                 VARCHAR2(64),
  node_id                  VARCHAR2(64),
  description              VARCHAR2(4000),
  subject                  VARCHAR2(255),
  parent_id                VARCHAR2(64),
  assignee                 VARCHAR2(64),
  claim_time               TIMESTAMP(6),
  name                     VARCHAR2(255),
  create_time              TIMESTAMP(6),
  start_time               TIMESTAMP(6),
  isblocking               VARCHAR2(20),
  end_time                 TIMESTAMP(6),
  duedate                  TIMESTAMP(6),
  priority                 NUMBER(6),
  category                 VARCHAR2(64),
  owner                    VARCHAR2(64),
  delegationstate          VARCHAR2(64),
  bizkey                   VARCHAR2(64),
  command_type             VARCHAR2(256),
  command_message          VARCHAR2(256),
  task_comment             VARCHAR2(4000),
  node_name                VARCHAR2(512),
  processdefinition_key    VARCHAR2(512),
  formuri                  VARCHAR2(256),
  taskgroup                VARCHAR2(64),
  tasktype                 VARCHAR2(64),
  processdefinition_name   VARCHAR2(512),
  iscancelled              VARCHAR2(64),
  issuspended              VARCHAR2(64),
  isopen                   VARCHAR2(64),
  isdraft                  VARCHAR2(64),
  expected_executiontime   NUMBER(14,2),
  agent                    VARCHAR2(64),
  admin                    VARCHAR2(64),
  formuriview              VARCHAR2(512),
  callactivity_instance_id VARCHAR2(256),
  command_id               VARCHAR2(64),
  pendingtaskid            VARCHAR2(64),
  archive_time             TIMESTAMP(6),
  completedescription      VARCHAR2(1024),
  processstart_time        TIMESTAMP(6),
  process_initiator        VARCHAR2(255),
  rev_                     INTEGER default 0,
  next_task_id             VARCHAR2(64),
  addsign_type             VARCHAR2(64)
)
;
alter table FOXBPM_RUN_TASK
  add primary key (ID);

create table FOXBPM_RUN_TASKIDENTITYLINK
(
  id                VARCHAR2(64) not null,
  type              VARCHAR2(64),
  user_id           VARCHAR2(64),
  group_id          VARCHAR2(64),
  group_type        VARCHAR2(64),
  task_id           VARCHAR2(64),
  include_exclusion VARCHAR2(64)
)
;
alter table FOXBPM_RUN_TASKIDENTITYLINK
  add primary key (ID);

create table FOXBPM_RUN_TOKEN
(
  id                    VARCHAR2(64) not null,
  name                  VARCHAR2(64),
  processinstance_id    VARCHAR2(64),
  node_id               VARCHAR2(128),
  parent_id             VARCHAR2(64),
  start_time            TIMESTAMP(6),
  end_time              TIMESTAMP(6),
  nodeenter_time        TIMESTAMP(6),
  archive_time          TIMESTAMP(6),
  issuspended           VARCHAR2(64),
  islock                VARCHAR2(64),
  isactive              VARCHAR2(64),
  issubprocessroottoken VARCHAR2(64),
  rev_                  INTEGER default 0,
  loop_count            INTEGER
)
;
alter table FOXBPM_RUN_TOKEN
  add primary key (ID);

create table FOXBPM_RUN_VARIABLE
(
  id                    VARCHAR2(64) not null,
  processinstance_id    VARCHAR2(64),
  processdefinition_id  VARCHAR2(255),
  processdefinition_key VARCHAR2(255),
  variable_key          VARCHAR2(64),
  variable_value        BLOB,
  classname             VARCHAR2(64),
  task_id               VARCHAR2(64),
  token_id              VARCHAR2(64),
  node_id               VARCHAR2(512),
  variable_type         VARCHAR2(45),
  biz_data              VARCHAR2(2048),
  rev_                  INTEGER default 0
)
;
alter table FOXBPM_RUN_VARIABLE
  add primary key (ID);

create table QRTZ_JOB_DETAILS
(
  sched_name        VARCHAR2(120) not null,
  job_name          VARCHAR2(200) not null,
  job_group         VARCHAR2(200) not null,
  description       VARCHAR2(250),
  job_class_name    VARCHAR2(250) not null,
  is_durable        VARCHAR2(1) not null,
  is_nonconcurrent  VARCHAR2(1) not null,
  is_update_data    VARCHAR2(1) not null,
  requests_recovery VARCHAR2(1) not null,
  job_data          BLOB
)
;
create index IDX_QRTZ_J_GRP on QRTZ_JOB_DETAILS (SCHED_NAME, JOB_GROUP);
create index IDX_QRTZ_J_REQ_RECOVERY on QRTZ_JOB_DETAILS (SCHED_NAME, REQUESTS_RECOVERY);
create unique index SYS_C009076 on QRTZ_JOB_DETAILS (SCHED_NAME, JOB_NAME, JOB_GROUP);
alter table QRTZ_JOB_DETAILS
  add primary key (SCHED_NAME, JOB_NAME, JOB_GROUP);

create table QRTZ_TRIGGERS
(
  sched_name     VARCHAR2(120) not null,
  trigger_name   VARCHAR2(200) not null,
  trigger_group  VARCHAR2(200) not null,
  job_name       VARCHAR2(200) not null,
  job_group      VARCHAR2(200) not null,
  description    VARCHAR2(250),
  next_fire_time NUMBER(13),
  prev_fire_time NUMBER(13),
  priority       NUMBER(13),
  trigger_state  VARCHAR2(16) not null,
  trigger_type   VARCHAR2(8) not null,
  start_time     NUMBER(13) not null,
  end_time       NUMBER(13),
  calendar_name  VARCHAR2(200),
  misfire_instr  NUMBER(2),
  job_data       BLOB
)
;
create index IDX_QRTZ_T_C on QRTZ_TRIGGERS (SCHED_NAME, CALENDAR_NAME);
create index IDX_QRTZ_T_G on QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_GROUP);
create index IDX_QRTZ_T_J on QRTZ_TRIGGERS (SCHED_NAME, JOB_NAME, JOB_GROUP);
create index IDX_QRTZ_T_JG on QRTZ_TRIGGERS (SCHED_NAME, JOB_GROUP);
create index IDX_QRTZ_T_NEXT_FIRE_TIME on QRTZ_TRIGGERS (SCHED_NAME, NEXT_FIRE_TIME);
create index IDX_QRTZ_T_NFT_MISFIRE on QRTZ_TRIGGERS (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME);
create index IDX_QRTZ_T_NFT_ST on QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_STATE, NEXT_FIRE_TIME);
create index IDX_QRTZ_T_NFT_ST_MISFIRE on QRTZ_TRIGGERS (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME, TRIGGER_STATE);
create index IDX_QRTZ_T_NFT_ST_MISFIRE_GRP on QRTZ_TRIGGERS (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME, TRIGGER_GROUP, TRIGGER_STATE);
create index IDX_QRTZ_T_N_G_STATE on QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_GROUP, TRIGGER_STATE);
create index IDX_QRTZ_T_N_STATE on QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, TRIGGER_STATE);
create index IDX_QRTZ_T_STATE on QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_STATE);
create unique index SYS_C009085 on QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);
alter table QRTZ_TRIGGERS
  add primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);
alter table QRTZ_TRIGGERS
  add foreign key (SCHED_NAME, JOB_NAME, JOB_GROUP)
  references QRTZ_JOB_DETAILS (SCHED_NAME, JOB_NAME, JOB_GROUP);

create table QRTZ_BLOB_TRIGGERS
(
  sched_name    VARCHAR2(120) not null,
  trigger_name  VARCHAR2(200) not null,
  trigger_group VARCHAR2(200) not null,
  blob_data     BLOB
)
;
create unique index SYS_C009109 on QRTZ_BLOB_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);
alter table QRTZ_BLOB_TRIGGERS
  add primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);
alter table QRTZ_BLOB_TRIGGERS
  add foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
  references QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

create table QRTZ_CALENDARS
(
  sched_name    VARCHAR2(120) not null,
  calendar_name VARCHAR2(200) not null,
  calendar      BLOB not null
)
;
create unique index SYS_C009114 on QRTZ_CALENDARS (SCHED_NAME, CALENDAR_NAME);
alter table QRTZ_CALENDARS
  add primary key (SCHED_NAME, CALENDAR_NAME);

create table QRTZ_CRON_TRIGGERS
(
  sched_name      VARCHAR2(120) not null,
  trigger_name    VARCHAR2(200) not null,
  trigger_group   VARCHAR2(200) not null,
  cron_expression VARCHAR2(120) not null,
  time_zone_id    VARCHAR2(80)
)
;
create unique index SYS_C009099 on QRTZ_CRON_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);
alter table QRTZ_CRON_TRIGGERS
  add primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);
alter table QRTZ_CRON_TRIGGERS
  add foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
  references QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

create table QRTZ_FIRED_TRIGGERS
(
  sched_name        VARCHAR2(120) not null,
  entry_id          VARCHAR2(95) not null,
  trigger_name      VARCHAR2(200) not null,
  trigger_group     VARCHAR2(200) not null,
  instance_name     VARCHAR2(200) not null,
  fired_time        NUMBER(13) not null,
  priority          NUMBER(13) not null,
  state             VARCHAR2(16) not null,
  job_name          VARCHAR2(200),
  job_group         VARCHAR2(200),
  is_nonconcurrent  VARCHAR2(1),
  requests_recovery VARCHAR2(1)
)
;
create index IDX_QRTZ_FT_INST_JOB_REQ_RCVRY on QRTZ_FIRED_TRIGGERS (SCHED_NAME, INSTANCE_NAME, REQUESTS_RECOVERY);
create index IDX_QRTZ_FT_JG on QRTZ_FIRED_TRIGGERS (SCHED_NAME, JOB_GROUP);
create index IDX_QRTZ_FT_J_G on QRTZ_FIRED_TRIGGERS (SCHED_NAME, JOB_NAME, JOB_GROUP);
create index IDX_QRTZ_FT_TG on QRTZ_FIRED_TRIGGERS (SCHED_NAME, TRIGGER_GROUP);
create index IDX_QRTZ_FT_TRIG_INST_NAME on QRTZ_FIRED_TRIGGERS (SCHED_NAME, INSTANCE_NAME);
create index IDX_QRTZ_FT_T_G on QRTZ_FIRED_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);
create unique index SYS_C009126 on QRTZ_FIRED_TRIGGERS (SCHED_NAME, ENTRY_ID);
alter table QRTZ_FIRED_TRIGGERS
  add primary key (SCHED_NAME, ENTRY_ID);

create table QRTZ_LOCKS
(
  sched_name VARCHAR2(120) not null,
  lock_name  VARCHAR2(40) not null
)
;
create unique index SYS_C009134 on QRTZ_LOCKS (SCHED_NAME, LOCK_NAME);
alter table QRTZ_LOCKS
  add primary key (SCHED_NAME, LOCK_NAME);

create table QRTZ_PAUSED_TRIGGER_GRPS
(
  sched_name    VARCHAR2(120) not null,
  trigger_group VARCHAR2(200) not null
)
;
create unique index SYS_C009117 on QRTZ_PAUSED_TRIGGER_GRPS (SCHED_NAME, TRIGGER_GROUP);
alter table QRTZ_PAUSED_TRIGGER_GRPS
  add primary key (SCHED_NAME, TRIGGER_GROUP);

create table QRTZ_SCHEDULER_STATE
(
  sched_name        VARCHAR2(120) not null,
  instance_name     VARCHAR2(200) not null,
  last_checkin_time NUMBER(13) not null,
  checkin_interval  NUMBER(13) not null
)
;
create unique index SYS_C009131 on QRTZ_SCHEDULER_STATE (SCHED_NAME, INSTANCE_NAME);
alter table QRTZ_SCHEDULER_STATE
  add primary key (SCHED_NAME, INSTANCE_NAME);

create table QRTZ_SIMPLE_TRIGGERS
(
  sched_name      VARCHAR2(120) not null,
  trigger_name    VARCHAR2(200) not null,
  trigger_group   VARCHAR2(200) not null,
  repeat_count    NUMBER(7) not null,
  repeat_interval NUMBER(12) not null,
  times_triggered NUMBER(10) not null
)
;
create unique index SYS_C009093 on QRTZ_SIMPLE_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);
alter table QRTZ_SIMPLE_TRIGGERS
  add primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);
alter table QRTZ_SIMPLE_TRIGGERS
  add foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
  references QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

create table QRTZ_SIMPROP_TRIGGERS
(
  sched_name    VARCHAR2(120) not null,
  trigger_name  VARCHAR2(200) not null,
  trigger_group VARCHAR2(200) not null,
  str_prop_1    VARCHAR2(512),
  str_prop_2    VARCHAR2(512),
  str_prop_3    VARCHAR2(512),
  int_prop_1    NUMBER(10),
  int_prop_2    NUMBER(10),
  long_prop_1   NUMBER(13),
  long_prop_2   NUMBER(13),
  dec_prop_1    NUMBER(13,4),
  dec_prop_2    NUMBER(13,4),
  bool_prop_1   VARCHAR2(1),
  bool_prop_2   VARCHAR2(1)
)
;
create unique index SYS_C009104 on QRTZ_SIMPROP_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);
alter table QRTZ_SIMPROP_TRIGGERS
  add primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);
alter table QRTZ_SIMPROP_TRIGGERS
  add foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
  references QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

create table TB_EXPENSE
(
  id                    VARCHAR2(64) not null,
  owner                 VARCHAR2(64),
  dept                  VARCHAR2(64),
  account               NUMBER,
  invoicetype           VARCHAR2(64),
  reason                VARCHAR2(252),
  create_time           VARCHAR2(64),
  processinstanceid     VARCHAR2(64)
)
;
alter table TB_EXPENSE
  add primary key (ID);

Insert into AU_ORGINFO (ORGID,SUPORGID,ORGNAME) values ('20001','2000','dept1');
Insert into AU_ORGINFO (ORGID,SUPORGID,ORGNAME) values ('200011','20001','dept2');
Insert into AU_ORGINFO (ORGID,SUPORGID,ORGNAME) values ('200012','20001','dept3'); 
Insert into AU_ROLEINFO (ROLEID,ROLENAME) values ('10001','ROLE1');
Insert into AU_ROLEINFO (ROLEID,ROLENAME) values ('10002','ROLE2'); 
Insert into AU_GROUP_RELATION (GUID,USERID,GROUPID,GROUPTYPE) values ('100000000000004','admin','200012','dept');
Insert into AU_GROUP_RELATION (GUID,USERID,GROUPID,GROUPTYPE) values ('100000000000003','admin','200011','dept');
Insert into AU_USERINFO (USERID,USERNAME,PASSWORD,EMAIL,TEL,IMAGE) values ('admin','admin','1','ft%25252563a.com','ddxx','admin.jpg');


