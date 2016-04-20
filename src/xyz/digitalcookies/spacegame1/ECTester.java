package xyz.digitalcookies.spacegame1;

import java.util.LinkedList;

import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.scene.Entity;
import xyz.digitalcookies.objective.scene.EntityContainer;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;

/** For testing entity containers. */
public class ECTester
{
	public void test()
	{
		EntityContainer ec = new EntityContainer();
		/* Test constructors. */
//		System.out.println(ec.getEntities().length);
//		ec = new EntityContainer(new EntityTest(), new EntityTest(), new EntityTest());
//		System.out.println(ec.getEntities().length);
//		LinkedList<Entity> ell = new LinkedList<Entity>();
//		ec = new EntityContainer(ell);
//		System.out.println(ec.getEntities().length);
//		ell.add(new EntityTest());
//		ell.add(new EntityTest());
//		System.out.println(ec.getEntities().length);
//		ec = new EntityContainer(ell);
//		System.out.println(ec.getEntities().length);
		/* Test basic add and remove (1 entity) and other basic methods. */
//		EntityTest et1 = new EntityTest();
//		System.out.println(et1);
//		System.out.println(ec.contains(et1));
//		ec.addEntity(et1);
//		System.out.println(ec.numEntities());
//		System.out.println(ec.getEntities().size());
//		System.out.println(ec.contains(et1));
//		System.out.println(ec.getEntities().get(0));
//		ec.addEntity(null);
//		System.out.println(ec.numEntities());
//		System.out.println(ec.contains(null));
//		// Basic remove
//		ec.removeEntity(et1);
//		System.out.println(ec.numEntities());
//		System.out.println(ec.contains(et1));
//		ec.removeEntity(et1);
//		System.out.println(ec.numEntities());
//		System.out.println(ec.contains(et1));
		/* Test multiple-entity add and remove methods. */
//		LinkedList<Entity> ell2 = new LinkedList<Entity>();
//		ell2.add(new EntityTest());
//		ell2.add(new EntityTest());
//		ec.addEntities(ell2);
//		System.out.println(ec.numEntities());
//		ec.addEntities(ell2.get(0), ell2.get(1));
//		System.out.println(ec.numEntities());
//		ec.removeEntities(ell2.get(0));
//		System.out.println(ec.numEntities());
//		ell2.add(new EntityTest());
//		ec.addEntities(ell2);
//		System.out.println(ec.numEntities());
//		ec.removeEntities(ell2);
//		System.out.println(ec.numEntities());
		/* Test clearing, etc. */
//		ec.addEntities(new EntityTest());
//		LinkedList<Entity> ell3 = new LinkedList<Entity>();
//		ell3.add(new EntityTest());
//		ell3.add(new EntityTest());
//		ec.addEntities(ell3);
//		System.out.println(ec.numEntities());
//		ec.clear();
//		System.out.println(ec.numEntities());
//		ec.addEntities();
//		System.out.println(ec.numEntities());
//		ec.addEntities(new EntityTest());
//		ec.removeEntities();
//		System.out.println(ec.numEntities());
//		ec.clear();
//		System.out.println(ec.numEntities());
//		ec.removeEntities(new EntityTest());
//		System.out.println(ec.numEntities());
		/* Test modifying list returned by getEntities(). */
//		ec.addEntities(new EntityTest());
//		System.out.println(ec.numEntities());
//		ec.getEntities().clear();
//		System.out.println(ec.numEntities());
	}
	
	public class EntityTest implements Entity
	{

		@Override
		public void render(RenderEvent arg0)
		{
//			System.out.println("Rendering Entity...");
//			System.out.println(this);
		}

		@Override
		public void update(EntityUpdateEvent arg0) {
			// TODO Auto-generated method stub
//			System.out.println("Updating Entity...");
//			System.out.println(this);
		}

		@Override
		public boolean utilizesBody() {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
}
