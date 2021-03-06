ALTER TABLE CUSTOMER ADD CONSTRAINT FOREIGN KEY(PARENT_CUSTOMER_ID) REFERENCES CUSTOMER(CUSTOMER_ID) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE CUSTOMER_POSITION ADD CONSTRAINT FOREIGN KEY(PARENT_CUSTOMER_ID) REFERENCES CUSTOMER(CUSTOMER_ID) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE GROUP_LOAN_COUNTER ADD CONSTRAINT FOREIGN KEY(GROUP_PERF_ID) REFERENCES GROUP_PERF_HISTORY(ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

UPDATE DATABASE_VERSION SET DATABASE_VERSION = 239 WHERE DATABASE_VERSION = 238;
