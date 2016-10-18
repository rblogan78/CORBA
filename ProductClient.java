
import ProductApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

public class ProductClient
{
	public static void main(String[] args)
	{
		try
		{
			ORB orb = ORB.init(args, null);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			
			String name = "first";
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			Product productRef = ProductHelper.narrow(ncRef.resolve_str(name));
			
			String description = productRef.getDescription();
			System.out.println(description);

		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
