package nz.co.udenbrothers.clockwork.dao;

import android.content.Context;

import java.util.ArrayList;

import nz.co.udenbrothers.clockwork.models.Worker;


public class WorkerDAO extends ModelDAO {

    protected void init(){
        table = "Worker";
        field("name", "text");
        field("email", "text");
    }

    public WorkerDAO(Context context){
        super(context);
    }

    public void add(Worker worker){
        key.put("name", worker.name);
        key.put("email", worker.email);
        worker.id = save();
    }

    public ArrayList<Worker> getAll(){
        ArrayList<Worker> workers = new ArrayList<>();
        load();
        while (cur.next()){
            Worker worker = new Worker();
            worker.id = cur.getLong("id");
            worker.name = cur.getStr("name");
            worker.email = cur.getStr("email");
            workers.add(worker);
        }
        return workers;
    }

    public ArrayList<Worker> getBy(String ki, String val){
        ArrayList<Worker> workers = new ArrayList<>();
        loadBy(ki, val);
        while (cur.next()){
            Worker worker = new Worker();
            worker.id = cur.getLong("id");
            worker.name = cur.getStr("name");
            worker.email = cur.getStr("email");
            workers.add(worker);
        }
        return workers;
    }
}
