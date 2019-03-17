FROM registry.njuics.cn/oracle/oracle-xe-11g:latest

RUN mkdir -p /u01/app/oracle/oradata && \
    chown -R oracle:dba /u01/app/oracle/oradata

VOLUME ["/u01/app/oracle/oradata"]

