Java Test task
==



Installation
==

```console
$ ./gradlew
```



Running
==

```console
$ ./gradlew bootRun
```

Application would listen on `http://localhost:8080/`.



Automatic Testing
==
```console
$ ./gradlew test
```



Manual Testing
==


Create Customers
===

```console
$ curl --request POST \
        --url http://localhost:8080/api/customer \
        --header 'content-type: application/json' \
        --data '{"name":"Alice", "surname": "Bright"}'

{"id":0,"name":"Alice","surname":"Bright"}

$ curl --request POST \
        --url http://localhost:8080/api/customer \
        --header 'content-type: application/json' \
        --data '{"name":"Bob", "surname": "Chunk"}'

{"id":1,"name":"Bob","surname":"Chunk"}

$ curl --request POST \
        --url http://localhost:8080/api/customer \
        --header 'content-type: application/json' \
        --data '{"name":"Charlie", "surname": "Drop"}'

{"id":2,"name":"Charlie","surname":"Drop"}
```

```console
$ curl --request GET \
        --url http://localhost:8080/api/customers \
        --header 'content-type: application/json' \
      2>/dev/null | jq
[
  {
    "id": 0,
    "name": "Alice",
    "surname": "Bright"
  },
  {
    "id": 1,
    "name": "Bob",
    "surname": "Chunk"
  },
  {
    "id": 2,
    "name": "Charlie",
    "surname": "Drop"
  }
]

$ curl --request GET \
        --url http://localhost:8080/api/customer/0 \
        --header 'content-type: application/json' \
      2>/dev/null | jq
{
  "id": 0,
  "name": "Alice",
  "surname": "Bright"
}
```


Create "Current Accounts" for the Customers
===

```console
$ curl --request POST \
        --url http://localhost:8080/api/account \
        --header 'content-type: application/json' \
        --data '{"customerID": 0, "initialCredit": 3}'
{"id":0,"customer":{"id":0,"name":"Alice","surname":"Bright"},"balance":3}

$ curl --request POST \
        --url http://localhost:8080/api/account \
        --header 'content-type: application/json' \
        --data '{"customerID": 1, "initialCredit": 0}'
{"id":1,"customer":{"id":1,"name":"Bob","surname":"Chunk"},"balance":0}

$ curl --request POST \
        --url http://localhost:8080/api/account \
        --header 'content-type: application/json' \
        --data '{"customerID": 2, "initialCredit": -1}'
{"id":2,"customer":{"id":2,"name":"Charlie","surname":"Drop"},"balance":-1}

$ curl --request POST \
        --url http://localhost:8080/api/account \
        --header 'content-type: application/json' \
        --data '{"customerID": 2, "initialCredit": 4}'
{"id":3,"customer":{"id":2,"name":"Charlie","surname":"Drop"},"balance":4}
```

```console
$ curl --request GET \
        --url http://localhost:8080/api/accounts \
        --header 'content-type: application/json' \
      2>/dev/null | jq
[
  {
    "id": 0,
    "customer": {
      "id": 0,
      "name": "Alice",
      "surname": "Bright"
    },
    "balance": 3
  },
  {
    "id": 1,
    "customer": {
      "id": 1,
      "name": "Bob",
      "surname": "Chunk"
    },
    "balance": 0
  },
  {
    "id": 2,
    "customer": {
      "id": 2,
      "name": "Charlie",
      "surname": "Drop"
    },
    "balance": -1
  },
  {
    "id": 3,
    "customer": {
      "id": 2,
      "name": "Charlie",
      "surname": "Drop"
    },
    "balance": 4
  }
]
```

```console
$ curl --request GET \
        --url http://localhost:8080/api/account/0 \
        --header 'content-type: application/json' \
      2>/dev/null | jq
{
  "id": 0,
  "customer": {
    "id": 0,
    "name": "Alice",
    "surname": "Bright"
  },
  "balance": 3,
  "transactions": [
    {
      "id": 0,
      "amount": 3
    }
  ]
}

$ curl --request GET \
        --url http://localhost:8080/api/account/1 \
        --header 'content-type: application/json' \
      2>/dev/null | jq
{
  "id": 1,
  "customer": {
    "id": 1,
    "name": "Bob",
    "surname": "Chunk"
  },
  "balance": 0,
  "transactions": []
}

$ curl --request GET \
        --url http://localhost:8080/api/account/2 \
        --header 'content-type: application/json' \
      2>/dev/null | jq
{
  "id": 2,
  "customer": {
    "id": 2,
    "name": "Charlie",
    "surname": "Drop"
  },
  "balance": -1,
  "transactions": [
    {
      "id": 1,
      "amount": -1
    }
  ]
}

$ curl --request GET \
        --url http://localhost:8080/api/account/3 \
        --header 'content-type: application/json' \
      2>/dev/null | jq
{
  "id": 3,
  "customer": {
    "id": 2,
    "name": "Charlie",
    "surname": "Drop"
  },
  "balance": 4,
  "transactions": [
    {
      "id": 2,
      "amount": 4
    }
  ]
}
```

Create Transaction towards "Current Account"
===

```console
$ curl --request POST \
        --url http://localhost:8080/api/transaction \
        --header 'content-type: application/json' \
        --data '{"accountID": 3, "amount": 3}'
{"id":3,"account":{"id":3,"customer":{"id":2,"name":"Charlie","surname":"Drop"},"balance":7},"amount":3}

$ curl --request GET \
        --url http://localhost:8080/api/account/3 \
        --header 'content-type: application/json' \
      2>/dev/null | jq
{
  "id": 3,
  "customer": {
    "id": 2,
    "name": "Charlie",
    "surname": "Drop"
  },
  "balance": 7,
  "transactions": [
    {
      "id": 2,
      "amount": 4
    },
    {
      "id": 3,
      "amount": 3
    }
  ]
}
```
