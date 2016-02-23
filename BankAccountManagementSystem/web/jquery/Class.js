var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var Model = (function () {
    function Model() {
        this.id = null;
        this.createdAt = this.updatedAt = new Date();
        this.deletedAt = null;
    }
    return Model;
}());
var Customer = (function (_super) {
    __extends(Customer, _super);
    function Customer(name, username, password) {
        _super.call(this);
        this.name = name;
        this.username = username;
        this.password = password;
        this.accounts = new Array();
    }
    Customer.prototype.addAccount = function (ac) {
        this.accounts.push(ac);
    };
    return Customer;
}(Model));
var Bank = (function (_super) {
    __extends(Bank, _super);
    function Bank(name) {
        _super.call(this);
        this.name = name;
        this.accounts = new Array();
        this.customers = new Array();
    }
    Bank.prototype.addAccount = function (ac) {
        this.accounts.push(ac);
        this.customers.push(ac.customer);
    };
    return Bank;
}(Model));
var Account = (function (_super) {
    __extends(Account, _super);
    function Account(acNo, c, b, balance) {
        _super.call(this);
        this.accountNo = acNo;
        this.customer = c;
        this.bank = b;
        this.balance = balance;
        this.customer.addAccount(this);
        this.bank.addAccount(this);
    }
    return Account;
}(Model));
var b1 = new Bank("IVE Bank");
var c = new Customer("Samuel", "ssss", "pwd");
var a = new Account("A1", c, b1, 1000);
