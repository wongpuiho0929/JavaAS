class Model {
    id : String;
    createdAt : Date;
    updatedAt: Date;
    deletedAt: Date;
}

class Customer extends Model {
    accounts: Account[];
    name: String;
    username: String;
    password: String;
    address: String;
    tel: String;

}

class Bank extends Model {
    name: String;
    customers: Customer[];
    accounts: Account[];
    address: String;
    tel: String;
}

class Account extends Model {
    accountNo : String;
    customer : Customer;
    bank : Bank;
    balance : Number;
};
