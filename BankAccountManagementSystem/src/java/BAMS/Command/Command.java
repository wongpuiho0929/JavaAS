package BAMS.Command;

import BAMS.DAO.DAO;
import BAMS.Enum.Action;
import BAMS.Enum.Target;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Command {

    public abstract void setting(HttpServletRequest request, HttpServletResponse response);

    public abstract void run();
    
    public abstract void log();

    public static Command getCommand(Target target, Action action) {

        Command c = null;
        switch (target) {
            case customer:
                switch (action) {
                    case create:
                        c = new CreateCustomerCommand();
                        break;
                    case edit:
                        c = new CreateCustomerCommand();
                        break;
                    case delete:
                        c = new CreateCustomerCommand();
                        break;
                }
                break;

            case account:
                switch (action) {
                    case create:
                        c = new CreateCustomerCommand();
                        break;
                    case edit:
                        c = new CreateCustomerCommand();
                        break;
                    case delete:
                        c = new CreateCustomerCommand();
                        break;
                }
                break;
            case bank:
                switch (action) {
                    case create:
                        c = new CreateCustomerCommand();
                        break;
                    case edit:
                        c = new CreateCustomerCommand();
                        break;
                    case delete:
                        c = new CreateCustomerCommand();
                        break;
                }
                break;
            case currency:
                switch (action) {
                    case create:
                        c = new CreateCustomerCommand();
                        break;
                    case edit:
                        c = new CreateCustomerCommand();
                        break;
                    case delete:
                        c = new CreateCustomerCommand();
                        break;
                }
                break;
            case exchangeRate:
                switch (action) {
                    case create:
                        c = new CreateCustomerCommand();
                        break;
                    case edit:
                        c = new CreateCustomerCommand();
                        break;
                    case delete:
                        c = new CreateCustomerCommand();
                        break;
                }
                break;
            case history:
                switch (action) {
                    case create:
                        c = new CreateCustomerCommand();
                        break;
                    case edit:
                        c = new CreateCustomerCommand();
                        break;
                    case delete:
                        c = new CreateCustomerCommand();
                        break;
                }
                break;
            case user:
                switch (action) {
                    case create:
                        c = new CreateCustomerCommand();
                        break;
                    case edit:
                        c = new CreateCustomerCommand();
                        break;
                    case delete:
                        c = new CreateCustomerCommand();
                        break;
                }
                break;

        }
        return c;
    }

}
