package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier apple = new Supplier("Apple", "Computers");
        supplierDataStore.add(apple);
        Supplier codeCoolShop = new Supplier("CodeCoolShop", "Gadgets");
        supplierDataStore.add(codeCoolShop);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory mobile = new ProductCategory("Mobile", "Hardware", "Mobile phone, shortly mobile. A pretty small but powerful device. Used for contacting people by phone or on the internet.");
        productCategoryDataStore.add(mobile);
        ProductCategory gadget = new ProductCategory("Gadget", "Item", "Interesting and useful gadgets helping us in our every-day life.");

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("Escape Preventing Dog Harness", 35, "USD", "Patented harness that prevents puppies or small adult dogs from escaping fenced-in areas.", gadget, codeCoolShop));
        productDataStore.add(new Product("Hot-Dog slicer", 20, "USD", "Create fun bite-size pieces.", gadget, codeCoolShop));
        productDataStore.add(new Product("Pet Sweeper", 50, "USD", "Animal powered debris removal system will keep your home tidy & your pets busy.", gadget, codeCoolShop));
        productDataStore.add(new Product("Sleep at Work Stickers", 5, "USD", "To help prevent getting caught sleeping.", gadget, codeCoolShop));
        productDataStore.add(new Product("Banana Slicer", 20, "USD", "Faster, safer than using a knife.", gadget, codeCoolShop));
        productDataStore.add(new Product("Bacon Flavored Toothpaste", 33, "USD", "Each tube contains 2.5 oz (70 g) of potent BACON paste.", gadget, codeCoolShop));


    }
}
