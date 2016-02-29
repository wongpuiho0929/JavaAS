package BAMS.Command;

import BAMS.DAO.DAO;
import BAMS.Model.History;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateCustomerCommand extends Command {

    private HttpServletRequest request;
    private HttpServletResponse response;

    @Override
    public void run() {
        /* some code */
        
        
        log();
    }

    @Override
    public void setting(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public void log() {
        History h = new History();
        /* some code */
        
        DAO.historyDB.create(h);
    }

}
