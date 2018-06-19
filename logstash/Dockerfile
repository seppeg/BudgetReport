FROM docker.elastic.co/logstash/logstash:6.2.2
COPY ./config/ /etc/logstash/conf.d/
CMD ["logstash","-f","/etc/logstash/conf.d/logstash.conf"]