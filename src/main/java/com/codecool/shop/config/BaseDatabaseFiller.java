package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.implementation.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.SupplierDaoJDBC;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;


import java.util.ArrayList;
import java.util.List;

public class BaseDatabaseFiller {
    private static int productId = 1;

    private ProductCategoryDao categoryDao = ProductCategoryDaoJDBC.getInstance();
    private SupplierDao supplierDao = SupplierDaoJDBC.getInstance();
    private ProductDao productDao = ProductDaoJDBC.getInstance();

    private List<Supplier> suppliers = new ArrayList<>();
    private List<ProductCategory> categories = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    private Supplier amazon = new Supplier("Amazon", "Digital content and services");
    private Supplier lenovo = new Supplier("Lenovo", "Computers");
    private Supplier apple = new Supplier("Apple", "Computers");
    private Supplier codeCoolShop = new Supplier("CodeCoolShop", "Gadgets");

    private ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
    private ProductCategory mobile = new ProductCategory("Mobile", "Hardware", "Mobile phone, shortly mobile. A pretty small but powerful device. Used for contacting people by phone or on the internet.");
    private ProductCategory gadget = new ProductCategory("Gadget", "Item", "Interesting and useful gadgets helping us in our every-day life.");

    private void fillSuppliers() {
        suppliers.add(amazon);
        suppliers.add(lenovo);
        suppliers.add(apple);
        suppliers.add(codeCoolShop);
    }

    private void fillCategories() {
        categories.add(tablet);
        categories.add(mobile);
        categories.add(gadget);
    }

    private void fillProducts() {
        products.add(new Product("Amazon Fire", 49, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        products.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        products.add(new Product("Amazon Fire HD 8", 89, "USD", "The latest Fire HD 8 tablet from Amazon is a great value for media consumption.", tablet, amazon));
        products.add(new Product("Escape Preventing Dog Harness", 35, "USD", "Patented harness that prevents puppies or small adult dogs from escaping fenced-in areas.", gadget, codeCoolShop));
        products.add(new Product("Hot-Dog slicer", 20, "USD", "Create fun bite-size pieces.", gadget, codeCoolShop));
        products.add(new Product("Pet Sweeper", 50, "USD", "Animal powered debris removal system will keep your home tidy & your pets busy.", gadget, codeCoolShop));
        products.add(new Product("Sleep at Work Stickers", 5, "USD", "To help prevent getting caught sleeping.", gadget, codeCoolShop));
        products.add(new Product("Banana Slicer", 20, "USD", "Faster, safer than using a knife.", gadget, codeCoolShop));
        products.add(new Product("Bacon Flavored Toothpaste", 33, "USD", "Each tube contains 2.5 oz (70 g) of potent BACON paste.", gadget, codeCoolShop));
    }

    private void addSuppliersToDatabase(List<Supplier> suppliers) {
        suppliers.forEach(supplierDao::add);
    }

    private void addCategoriesToDatabase(List<ProductCategory> categories) {
        categories.forEach(categoryDao::add);
    }

    private void addProductsToDatabase(List<Product> products) {
        products.forEach(product -> product.setId(productId++));
        products.forEach(productDao::add);
    }

    public void fillDatabase() {
        fillSuppliers();
        fillCategories();
        fillProducts();

        addSuppliersToDatabase(suppliers);
        addCategoriesToDatabase(categories);
        addProductsToDatabase(products);
    }
}
