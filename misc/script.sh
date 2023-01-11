docker-compose -f docker-compose-single.yml up -d
docker-compose -f docker-compose-single.yml exec kafka kafka-topics --create --topic plachat --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1
docker-compose -f docker-compose-single.yml exec kafka kafka-topics --describe --topic plachat --bootstrap-server kafka:9092
docker-compose -f docker-compose-single.yml exec kafka bash
$ kafka-console-consumer --topic plachat --bootstrap-server kafka:9092
$ kafka-console-producer --topic plachat --broker-list kafka:9092
docker-compose -f docker-compose-single.yml down