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
				
     		ProductImpl productImpl = new productImpl();
        productImpl.setORB(orb);
    
        // get object reference from the servant
        org.omg.CORBA.Object ref = rootpoa.servant_to_reference(productImpl);
        Product href = productHelper.narrow(ref);

        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
    
        String name = "first";
        NameComponent[] path = ncRef.to_name(name);
        ncRef.rebind(path, href);

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

  public void setORB(ORB orb_val){
    orb = orb_val;
  }
  
  public String getDescription(){
    return "Apples";
  }
  
  public void shutdown(){
    orb.shutdown(false);
  }
}