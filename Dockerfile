FROM adzerk/boot-clj

RUN apt-get update && apt-get install -y supervisor
ADD . /app
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf
RUN cd /app

EXPOSE 8080
ENTRYPOINT "/bin/bash"
CMD ["/usr/bin/supervisord"]
