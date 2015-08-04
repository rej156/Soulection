FROM adzerk/boot-clj

ADD . /app
RUN cd /app

WORKDIR /app
EXPOSE 3000

CMD ["prod"]
