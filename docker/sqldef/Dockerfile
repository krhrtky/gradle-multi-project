FROM debian:bookworm

RUN apt update \
    && apt install -y wget \
    && wget https://github.com/k0kubun/sqldef/releases/download/v0.15.27/mysqldef_linux_amd64.tar.gz -O mysqldef.tar.gz \
    && tar -zxvf mysqldef.tar.gz -C /bin \
    && rm mysqldef.tar.gz \
    && apt clean
