
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
			
			String name1 = "first";
			NamingContextExt ncRef1 = NamingContextExtHelper.narrow(objRef);
			Product productRef1 = ProductHelper.narrow(ncRef1.resolve_str(name1));
			
			String description1 = productRef1.getDescription();
			System.out.println(description1);

			String name2 = "second";
			NamingContextExt ncRef2 = NamingContextExtHelper.narrow(objRef);
			Product productRef2 = ProductHelper.narrow(ncRef2.resolve_str(name2));
			
			String description2 = productRef2.getDescription();
			System.out.println(description2);


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
