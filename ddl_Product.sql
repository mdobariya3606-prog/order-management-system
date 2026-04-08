CREATE TABLE product
(
    p_id      BIGINT       NOT NULL,
    name      VARCHAR(255) NULL,
    price     DOUBLE       NOT NULL,
    gst       DOUBLE       NOT NULL,
    quantity  BIGINT       NOT NULL,
    threshold BIGINT       NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (p_id)
);