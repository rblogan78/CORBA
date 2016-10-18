import ProductApp.*; 
import org.omg.CosNaming.*; 
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*; 
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;

public class ProductServer
{
	public static void main(String[] args)
	{
    	try
    	{
    		ORB orb = ORB.init(args, null);
			  POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			  rootpoa.the_POAManager().activate();
				
     		ProductImpl productImpl1 = new ProductImpl();
        productImpl1.setORB(orb);
        productImpl1.setDescription("Apple");

        ProductImpl productImpl2 = new ProductImpl();
        productImpl2.setORB(orb);
        productImpl2.setDescription("Pear");

    
        // get object reference from the servant
        org.omg.CORBA.Object ref1 = rootpoa.servant_to_reference(productImpl1);
        Product href1 = ProductHelper.narrow(ref1);

        org.omg.CORBA.Object objRef1 = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef1 = NamingContextExtHelper.narrow(objRef1);
    
        String name1 = "first";
        NameComponent[] path1 = ncRef1.to_name(name1);
        ncRef1.rebind(path1, href1);

        org.omg.CORBA.Object ref2 = rootpoa.servant_to_reference(productImpl2);
        Product href2 = ProductHelper.narrow(ref2);

        org.omg.CORBA.Object objRef2 = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef2 = NamingContextExtHelper.narrow(objRef2);
    
        String name2 = "second";
        NameComponent[] path2 = ncRef2.to_name(name2);
        ncRef2.rebind(path2, href2);

        orb.run();

    	}
		catch (Exception e)
		{

			e.printStackTrace();
		}
	}
}

class ProductImpl extends ProductPOA{

  private ORB orb;
  private String description;

  public void setORB(ORB orb_val){
    orb = orb_val;
  }
  
  public String getDescription(){
    return description;
  }

  public void setDescription(String desc){
    description = desc;
  }
  
  public void shutdown(){
    orb.shutdown(false);
  }
}