FROM fluent/fluentd:v0.12-debian
COPY fluent.conf /fluentd/etc
RUN ["gem", "install", "fluent-plugin-elasticsearch", "--no-rdoc", "--no-ri", "--version", "1.9.2"]

