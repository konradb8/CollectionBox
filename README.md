# Collection box application
#### Collection boxes management app for Fundraising Events
## How to build and run:
```bash
mvn spring-boot:run
```

## Available API Endpoints:
### Base URL:
http://localhost:8080/v1/

| Method   | Endpoint                                  | Description                                             |
|----------|-------------------------------------------|---------------------------------------------------------|
| `POST`   | `/v1/event`                               | Create new Fundraising Event                            |
| `POST`   | `/v1/collection-box`                      | Register new Collection Box                             |
| `GET`    | `/v1/collection-box/boxlist`              | Get list of Collection Boxes                            |
| `DELETE` | `/v1/collection-box/{box}`                | Deletes the Collection Box                              | 
| `PUT`    | `/v1/collection-box/assign/{box}/{event}` | Assign the box to the Fundraising Event                 |
| `PUT`    | `/v1/collection-box/addfunds`             | Put money in the Collection Box                         |
| `PUT`    | `/v1/collection-box/transfer/{box}`       | Transfer money from Collection Box to Fundraising Event |
| `GET`    | `/v1/event/report`                        | Get a report of Fundraising Events                      |



## Example:
#### Endpoint `POST`: http://localhost:8080/v1/event
#### Request Body:
```json
{
    "name":"Event1",
    "currency":"EUR"
}
```
#### Output:
![img1](.media/img1.png)


#### Endpoint `POST`: http://localhost:8080/v1/collection-box
#### Request Body:
```json
{
    "uid":"BOX1"
}
```
#### Output:
![img2](.media/img2.png)

#### Endpoint `GET`: http://localhost:8080/v1/collection-box/boxlist
#### Output:
![img3](.media/img3.png)




#### Endpoint `DELETE`: http://localhost:8080/v1/collection-box/BOX1
#### Output:
![img4](.media/img4.png)
![img5](.media/img5.png)



#### Endpoint `PUT`: http://localhost:8080/v1/collection-box/assign/BOX3/Event1
#### Output:
![img6](.media/img6.png)
![img7](.media/img7.png)

#### Endpoint `PUT`: http://localhost:8080/v1/collection-box/addfunds
#### Request Body:
```json

{
    "currency":"PLN",
    "amount":"120",
    "boxUid":"BOX3"
}
```
#### Output:
![img8](.media/img8.png)
![img9](.media/img9.png)

#### Endpoint `PUT`: http://localhost:8080/v1/collection-box/transfer/BOX3
#### Output:
![img10](.media/img10.png)
#### Endpoint `GET`: http://localhost:8080/v1/event/report
#### Output:
![img11](.media/img11.png)