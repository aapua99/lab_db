import entity.FruitsEntity;
import entity.ShopEntity;
import entity.SuppliersEntity;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {
            while (true) {
                System.out.println("1)Read table\n2)Insert Table\n3)Update Table\n4)Delate table\n");
                Scanner scanner = new Scanner(System.in);
                int var = scanner.nextInt();
                switch (var) {
                    case 1:
                        readTable(session);
                        break;
                    case 2:
                        insertData(session);
                        break;
                    case 3:
                        updateData(session);
                        break;
                    case 4:
                        deleteDataSuppliers(session);
                        break;
                }
            }

        } finally {
            session.close();
        }
    }

    private static void readTable(Session session) {
        //region Read table Suppliers
        Query query = session.createQuery("from " + "SuppliersEntity ");
        System.out.format("\nTable Suppliers --------------------\n");
        System.out.format("%-18s %-12s \n", "Name", "City");
        for (Object obj : query.list()) {
            SuppliersEntity supplier = (SuppliersEntity) obj;
            System.out.format("%-18s %-12s\n", supplier.getName(),
                    supplier.getCity());
            for (FruitsEntity i : supplier.getFruits()) {
                System.out.printf("%30s %-12s %-10s %-12s \n", "-", i.getName(), String.valueOf(i.getPrice()), i.getShop());
            }
        }
        //endregion

        //region Read table Fruits
        System.out.format("\n\n\nTable Fruits --------------------\n");
        query = session.createQuery("from FruitsEntity ");
        System.out.printf("%-12s %-10s %12s\n", "Name", "Price", "Name of shop");
        for (Object obj : query.list()) {
            FruitsEntity fruits = (FruitsEntity) obj;
            System.out.printf("%-12s %-10s %12s\n", fruits.getName(), String.valueOf(fruits.getPrice()), fruits.getShop());

            for (SuppliersEntity i : fruits.getSuppliers()) {
                System.out.printf("%40s %-12s %-12s\n", "-", i.getName(), i.getCity());

            }
        }//endregion

        //region Read table Shop
        System.out.format("\n\n\nTable Shop ----------------------------------------------------\n");
        query = session.createQuery("from ShopEntity ");
        System.out.printf("%-20s %-25s %-18s\n", "Name", "Address", "Manager");
        for (Object object : query.list()) {
            ShopEntity shop = (ShopEntity) object;
            System.out.printf("%-20s %-25s %-18s\n", shop.getName(), shop.getManager(), shop.getAddress());
        }
        //endregion

    }

    private static void insertData(Session session) {
        System.out.println("1) insert shop\n2)insert suppliers\n3)insert fruits\n4)insert relation suppliers and fruits");
        Scanner scanner = new Scanner(System.in);
        int var = scanner.nextInt();
        //region Insert shop
        if (var == 1) {
            System.out.println("Write name");
            String name = scanner.next();
            System.out.println("Write address");
            String address = scanner.next();
            System.out.println("Write manager");
            String manager = scanner.next();
            ShopEntity shopEntity = new ShopEntity(name, address, manager);
            session.beginTransaction();
            session.save(shopEntity);
            session.getTransaction().commit();
        }
        //endregion

        //region Insert supplier
        if (var == 2) {
            System.out.println("Write name");
            String name = scanner.next();
            System.out.println("Write city");
            String city = scanner.next();
            SuppliersEntity suppliersEntity = new SuppliersEntity(name, city);
            session.beginTransaction();
            session.save(suppliersEntity);
            session.getTransaction().commit();
        }
        //endregion

        //region Insert fruit
        if (var == 3) {
            System.out.println("Write name");
            String name = scanner.next();
            System.out.println("Write price");
            int price = scanner.nextInt();
            System.out.println("Write shop");
            String shop = scanner.next();
            FruitsEntity fruitsEntity = new FruitsEntity(name, price, shop);
            session.beginTransaction();
            session.save(fruitsEntity);
            session.getTransaction().commit();
        }
        //endregion

        //region Insert pair
        if (var == 4) {
            Scanner input = new Scanner(System.in);
            System.out.println("Choose fruit:");
            String fruit = input.next();
            System.out.println("Choose supplier:");
            String supplier = input.next();

            Query query = session.createQuery("from " + "FruitsEntity where name = :code");
            query.setParameter("code", fruit);

            if (!query.list().isEmpty()) {
                FruitsEntity fruitsEntity = (FruitsEntity) query.list().get(0);
                query = session.createQuery("from " + "SuppliersEntity where name = :code");
                query.setParameter("code", supplier);
                if (!query.list().isEmpty()) {
                    //Give this book entity from query
                    SuppliersEntity suppliersEntity = (SuppliersEntity) query.list().get(0);
                    session.beginTransaction();
                    suppliersEntity.addFruit(fruitsEntity);
                    session.save(suppliersEntity);
                    session.getTransaction().commit();


                } else {
                    System.out.println("There is no the supplier");
                }
            } else {
                System.out.println("There is no this fruit");
            }

        }
        //endregion
    }

    private static void updateData(Session session) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nInput a name city: ");
        String city = input.next();
        System.out.println("Input new name city: ");
        String newCity = input.next();
        session.beginTransaction();
        Query query = session.createQuery("update SuppliersEntity set city=:code1  where city = :code2");
        query.setParameter("code1", newCity);
        query.setParameter("code2", city);
        int result = query.executeUpdate();
        session.getTransaction().commit();
    }

    private static void deleteDataSuppliers(Session session) {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a fruit: ");
        String fruit = input.next();
        System.out.println("Input a supplier: ");
        String supplier = input.next();

        Query query = session.createQuery("from " + "FruitsEntity where name = :code");
        query.setParameter("code", fruit);

        if (!query.list().isEmpty()) {
            //Give this person entity from query
            FruitsEntity fruitsEntity = (FruitsEntity) query.list().get(0);
            //search the book entity  from query
            query = session.createQuery("from " + "SuppliersEntity where name = :code");
            query.setParameter("code", supplier);
            if (!query.list().isEmpty()) {

                SuppliersEntity suppliersEntity = (SuppliersEntity) query.list().get(0);
                session.beginTransaction();
                suppliersEntity.deleteFruits(fruitsEntity);
                session.save(suppliersEntity);
                session.getTransaction().commit();

                //region Read table Suppliers
                query = session.createQuery("from " + "SuppliersEntity ");
                System.out.format("\nTable Suppliers --------------------\n");
                System.out.format("%-18s %-12s \n", "Name", "City");
                for (Object obj : query.list()) {
                    SuppliersEntity sup = (SuppliersEntity) obj;
                    System.out.format("%-18s %-12s\n", sup.getName(),
                            sup.getCity());
                    for (FruitsEntity i : sup.getFruits()) {
                        System.out.printf("%30s %-12s %-10s %-12s \n", "-", i.getName(), String.valueOf(i.getPrice()), i.getShop());
                    }
                }
                //endregion
            } else {
                System.out.println("There is no the supplier");
            }
        } else {
            System.out.println("There is no this fruit");
        }

    }


}