-- Initial schema for price tracking

create table if not exists users (
    id bigserial primary key,
    email varchar(254) not null unique,
    display_name varchar(100),
    active boolean not null default true,
    created_at timestamptz not null default now()
);

create table if not exists retailers (
    id bigserial primary key,
    name varchar(160) not null unique,
    website_url varchar(255)
);

create table if not exists products (
    id bigserial primary key,
    sku varchar(120) unique,
    name varchar(255) not null,
    description varchar(1024),
    image_url varchar(2048)
);

create table if not exists price_snapshots (
    id bigserial primary key,
    product_id bigint not null references products(id),
    retailer_id bigint not null references retailers(id),
    price numeric(14,2) not null,
    currency varchar(8),
    timestamp timestamptz not null default now(),
    product_url varchar(2048)
);
create index if not exists idx_price_snapshots_product_time on price_snapshots(product_id, timestamp);
create index if not exists idx_price_snapshots_retailer_time on price_snapshots(retailer_id, timestamp);

create table if not exists wishlists (
    id bigserial primary key,
    name varchar(160) not null,
    user_id bigint not null references users(id),
    created_at timestamptz not null default now()
);
create index if not exists idx_wishlists_user on wishlists(user_id);

create table if not exists wishlist_items (
    id bigserial primary key,
    wishlist_id bigint not null references wishlists(id) on delete cascade,
    product_id bigint not null references products(id),
    target_price numeric(14,2),
    added_at timestamptz not null default now(),
    constraint uk_wishlist_product unique (wishlist_id, product_id)
);
create index if not exists idx_wishlist_items_product on wishlist_items(product_id);

create table if not exists notification_preferences (
    id bigint primary key, -- matches users.id (1-1)
    user_id bigint not null unique references users(id) on delete cascade,
    email_enabled boolean not null default true,
    webhooks_enabled boolean not null default false,
    webhook_url varchar(2048)
);
