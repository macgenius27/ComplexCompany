/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package complexcompany;



import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

class ComplexCompany implements Serializable {
  private String name;

  private ComplexEmployee president;

  private Vector departments;

  public ComplexCompany(String name) {
    this.name = name;
    departments = new Vector();
  }

  public String getName() {
    return this.name;
  }

  public void addDepartment(ComplexDepartment dept) {
    departments.addElement(dept);
  }

  public ComplexEmployee getPresident() {
    return this.president;
  }

  public void addPresident(ComplexEmployee e) {
    this.president = e;
  }

  public Iterator getDepartmentIterator() {
    return departments.iterator();
  }

  public void printCompanyObject() {
    System.out.println("The company name is " + getName());
    System.out.println("The company president is " + getPresident().getName());
    System.out.println(" ");

    Iterator i = getDepartmentIterator();
    while (i.hasNext()) {
      ComplexDepartment d = (ComplexDepartment) i.next();
      System.out.println("   The department name is " + d.getName());
      System.out.println("   The department manager is " + d.getManager().getName());
      System.out.println(" ");
    }
  }

}

class ComplexDepartment implements Serializable {
  private String name;

  private ComplexEmployee manager;

  public ComplexDepartment(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public ComplexEmployee getManager() {
    return this.manager;
  }

  public void addManager(ComplexEmployee e) {
    manager = e;
  }
}

class ComplexEmployee implements Serializable {
  private String name;

  private int salary;

  /** Creates a new instance of ComplexEmployee */
  public ComplexEmployee(String name, int salary) {
    this.name = name;
    this.salary = salary;
  }

  public String getName() {
    return name;
  }

  public int getSalary() {
    return this.salary;
  }
}

/*public*/ class ComplexSocketServer {

  public static void main(String args[]) throws Exception {
    ServerSocket servSocket;
    Socket fromClientSocket;
    int cTosPortNumber = 1777;
    String str;
    ComplexCompany comp;

    servSocket = new ServerSocket(cTosPortNumber);
    System.out.println("Waiting for a connection on " + cTosPortNumber);

    fromClientSocket = servSocket.accept();

    ObjectOutputStream oos = new ObjectOutputStream(fromClientSocket.getOutputStream());

    ObjectInputStream ois = new ObjectInputStream(fromClientSocket.getInputStream());

    while ((comp = (ComplexCompany) ois.readObject()) != null) {
      comp.printCompanyObject();

      oos.writeObject("bye bye");
      break;
    }
    oos.close();

    fromClientSocket.close();
  }
}





/*public*/ class ComplexSocketClient {

  public static void main(String args[]) throws Exception {
    Socket socket1;
    int portNumber = 1777;
    String str = "";

    socket1 = new Socket(InetAddress.getLocalHost(), portNumber);

    ObjectInputStream ois = new ObjectInputStream(socket1.getInputStream());

    ObjectOutputStream oos = new ObjectOutputStream(socket1.getOutputStream());

    ComplexCompany comp = new ComplexCompany("A");
    ComplexEmployee emp0 = new ComplexEmployee("B", 1000);
    comp.addPresident(emp0);

    ComplexDepartment sales = new ComplexDepartment("C");
    ComplexEmployee emp1 = new ComplexEmployee("D", 1200);
    sales.addManager(emp1);
    comp.addDepartment(sales);

    ComplexDepartment accounting = new ComplexDepartment("E");
    ComplexEmployee emp2 = new ComplexEmployee("F", 1230);
    accounting.addManager(emp2);
    comp.addDepartment(accounting);

    ComplexDepartment maintenance = new ComplexDepartment("Maintenance");
    ComplexEmployee emp3 = new ComplexEmployee("Greg Hladlick", 1020);
    maintenance.addManager(emp3);
    comp.addDepartment(maintenance);

    oos.writeObject(comp);

    while ((str = (String) ois.readObject()) != null) {
      System.out.println(str);
      oos.writeObject("bye");

      if (str.equals("bye"))
        break;
    }

    ois.close();
    oos.close();
    socket1.close();
  }

}





 // THE NAME OF THE CLASS IS  ComplexCompany


