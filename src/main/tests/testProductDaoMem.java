import com.codecool.shop.dao.ElementNotFoundException;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import java.util.ArrayList;

import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

public class testProductDaoMem {

    private static ProductDao testProductDao;
    private static ProductCategoryDao testCategoryDao;
    private static SupplierDao testSupplierDao;
    private static Product productForTest;
    private static Supplier supplierForTest;
    private static ProductCategory categoryForTest;
    private static boolean Mem = true;


    @BeforeAll
    public static void init() {
        System.out.println("Test started");
        if(Mem) {
            testProductDao = ProductDaoMem.getInstance();
            testCategoryDao = ProductCategoryDaoMem.getInstance();
            testSupplierDao = SupplierDaoMem.getInstance();

            Supplier amazon = new Supplier("Amazon", "Digital content and services");
            supplierForTest = amazon;
            testSupplierDao.add(amazon);
            Supplier apple = new Supplier("Apple", "Computers");
            testSupplierDao.add(apple);
            Supplier codeCoolShop = new Supplier("CodeCoolShop", "Gadgets");
            testSupplierDao.add(codeCoolShop);

            ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
            categoryForTest = tablet;
            testCategoryDao.add(tablet);
            ProductCategory mobile = new ProductCategory("Mobile", "Hardware", "Mobile phone, shortly mobile. A pretty small but powerful device. Used for contacting people by phone or on the internet.");
            testCategoryDao.add(mobile);
            ProductCategory gadget = new ProductCategory("Gadget", "Item", "Interesting and useful gadgets helping us in our every-day life.");

            productForTest = new Product("Amazon Fire", 49, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon);

            testProductDao.add(productForTest);
            testProductDao.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", gadget, codeCoolShop));
            testProductDao.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", gadget, codeCoolShop));
            testProductDao.add(new Product("Escape Preventing Dog Harness", 35, "USD", "Patented harness that prevents puppies or small adult dogs from escaping fenced-in areas.", gadget, codeCoolShop));
            testProductDao.add(new Product("Hot-Dog slicer", 20, "USD", "Create fun bite-size pieces.", gadget, codeCoolShop));
            testProductDao.add(new Product("Pet Sweeper", 50, "USD", "Animal powered debris removal system will keep your home tidy & your pets busy.", gadget, codeCoolShop));
            testProductDao.add(new Product("Sleep at Work Stickers", 5, "USD", "To help prevent getting caught sleeping.", gadget, codeCoolShop));
            testProductDao.add(new Product("Banana Slicer", 20, "USD", "Faster, safer than using a knife.", gadget, codeCoolShop));
            testProductDao.add(new Product("Bacon Flavored Toothpaste", 33, "USD", "Each tube contains 2.5 oz (70 g) of potent BACON paste.", gadget, codeCoolShop));
        }
    }

    @Tag("ProductDao")
    @Test
    public void tesProductFind(){
        assertDoesNotThrow(() -> testProductDao.find(1));
        try{
            assertEquals(productForTest,testProductDao.find(1));
        } catch(ElementNotFoundException enfe){System.out.println("this should not have happen");}
    }

    @Tag("ProductDao")
    @Test
    public void testFindProductName(){
        init();
        assertDoesNotThrow(() -> testProductDao.find(1));
        try{
            assertEquals("Amazon Fire",testProductDao.find(1).getName());
        } catch(ElementNotFoundException enfe){System.out.println("this should not have happen");}
    }

    @Tag("ProductDao")
    @Test
    public void testFindProductPrice(){
        assertDoesNotThrow(() -> testProductDao.find(1));
        try{
            assertEquals("49.0 USD",testProductDao.find(1).getPrice());
        } catch(ElementNotFoundException enfe){System.out.println("this should not have happen");}
    }

    @Tag("ProductDao")
    @Test
    public void testFindProductPriceNum(){
        assertDoesNotThrow(() -> testProductDao.find(1));
        try{
            assertEquals("49.0",testProductDao.find(1).getPriceNum());
        } catch(ElementNotFoundException enfe){System.out.println("this should not have happen");}
    }

    @Tag("ProductDao")
    @Test
    public void testFindProductDescription(){
        assertDoesNotThrow(() -> testProductDao.find(1));
        try{
            assertEquals("Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.",testProductDao.find(1).getDescription());
        } catch(ElementNotFoundException enfe){System.out.println("this should not have happen");}
    }

    @Tag("ProductDao")
    @Test
    public void testGetBySupplier(){
        ArrayList<Product> arrayForTest = new ArrayList<Product>();
        arrayForTest.add(productForTest);
        assertEquals(arrayForTest,testProductDao.getBy(supplierForTest));
    }

    @Tag("ProductDao")
    @Test
    public void testGetByCategory(){
        ArrayList<Product> arrayForTest = new ArrayList<Product>();
        arrayForTest.add(productForTest);
        assertEquals(arrayForTest,testProductDao.getBy(categoryForTest));
    }

    @Tag("ProductCategoryDao")
    @Test
    public void testCategoryFind(){
        assertEquals(categoryForTest,testCategoryDao.find(1));
    }

    @Tag("ProductCategoryDao")
    @Test
    public void testCategoryFindDepartment(){
        assertEquals("Hardware",testCategoryDao.find(1).getDepartment());
    }

    @Tag("ProductCategoryDao")
    @Test
    public void testCategoryFindDescription(){
        assertEquals("A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.",testCategoryDao.find(1).getDescription());
    }

    @Tag("ProductCategoryDao")
    @Test
    public void testCategoryFindName(){
        assertEquals("Tablet",testCategoryDao.find(1).getName());
    }

    @Tag("ProductCategoryDao")
    @Test
    public void testCategoryFindProducts(){
        ArrayList<Product> arrayForTest = new ArrayList<Product>();
        arrayForTest.add(productForTest);
        assertEquals(arrayForTest,testCategoryDao.find(1).getProducts());
    }

    @Tag("ProductSupplierDao")
    @Test
    public void testSupplierFind(){
        assertEquals(supplierForTest,testSupplierDao.find(1));
    }

    @Tag("ProductSupplierDao")
    @Test
    public void testSupplierFindProducts(){
        ArrayList<Product> arrayForTest = new ArrayList<Product>();
        arrayForTest.add(productForTest);
        assertEquals(arrayForTest,testSupplierDao.find(1).getProducts());
    }

    @Tag("ProductSupplierDao")
    @Test
    public void testSupplierFindDescription(){
        assertEquals("Digital content and services",testSupplierDao.find(1).getDescription());
    }

    @Tag("ProductSupplierDao")
    @Test
    public void testSupplierFindName(){
        assertEquals("Amazon",testSupplierDao.find(1).getName());
    }

    @Tag("ExceptionThrow")
    @Tag("ProductDao")
    @Test
    public void testProductFindThrowsException(){
        assertThrows(ElementNotFoundException.class,() -> testProductDao.find(0));
    }

    @Tag("ExceptionThrow")
    @Tag("ProductCategoryDao")
    @Test
    public void testCategoryFindThrowsException(){
        assertThrows(ElementNotFoundException.class,() -> testCategoryDao.find(0));
    }

    @Tag("ExceptionThrow")
    @Tag("ProductSupplierDao")
    @Test
    public void testSupplierFindThrowsException(){
        assertThrows(ElementNotFoundException.class,() -> testSupplierDao.find(0));
    }

}

