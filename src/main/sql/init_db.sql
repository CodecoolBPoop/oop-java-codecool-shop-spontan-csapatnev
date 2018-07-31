CREATE TABLE public.suppliers
(
    id serial PRIMARY KEY NOT NULL,
    name varchar(255) NOT NULL,
    description TEXT
);
CREATE UNIQUE INDEX suppliers_id_uindex ON public.suppliers (id);
CREATE UNIQUE INDEX suppliers_name_uindex ON public.suppliers (name);


CREATE TABLE public.product_categories
(
    id serial PRIMARY KEY NOT NULL,
    name varchar(255) NOT NULL,
    department varchar(255),
    description TEXT
);
CREATE UNIQUE INDEX product_categories_id_uindex ON public.product_categories (id);
CREATE UNIQUE INDEX product_categories_name_uindex ON public.product_categories (name);

CREATE TABLE public.product
(
    id serial PRIMARY KEY NOT NULL,
    name varchar(255) NOT NULL,
    description TEXT,
    image varchar(255),
    supplier_id int NOT NULL,
    currency varchar(255) NOT NULL,
    price int NOT NULL,
    category_id int NOT NULL,
    CONSTRAINT category_id_fk FOREIGN KEY (category_id) REFERENCES product_categories (id),
    CONSTRAINT supplier_id_fk FOREIGN KEY (supplier_id) REFERENCES suppliers (id)
);
CREATE UNIQUE INDEX product_id_uindex ON public.product (id);