FROM adzerk/boot-clj

ADD . /app
RUN cd /app

WORKDIR /app
EXPOSE 8080

CMD ["prod"]
