create table AmazonFeedSubmission (_id bigint generated by default as identity, completedProcessingDate timestamp, feedProcessingStatus varchar(255) not null, feedSubmissionId varchar(255) not null, feedSubmissionResult varchar(255), feedType varchar(255) not null, marketplaceId varchar(255) not null, messagesProcessed integer, messagesSuccessful integer, messagesWithError integer, messagesWithWarning integer, startedProcessingDate timestamp, submittedDate timestamp not null, primary key (_id));
create table Author (_id bigint generated by default as identity, email varchar(255) not null, name varchar(255) not null, uri varchar(255) not null, primary key (_id));
create table Brand (_id bigint not null, name varchar(255) not null, primary key (_id));
create table Brand_Rule (brand__id bigint not null, rule__id bigint not null);
create table Category (_id bigint generated by default as identity, code bigint not null, name varchar(255) not null, primary key (_id));
create table CustomAttribute (_id bigint generated by default as identity, name varchar(255) not null, unit varchar(255), value clob not null, primary key (_id));
create table DataFeed (_id bigint generated by default as identity, parseDateDateTime timestamp default CURRENT_TIMESTAMP, root__id bigint not null, primary key (_id));
create table ImageLink (_id bigint generated by default as identity, label varchar(255) not null, link binary(65535), primary key (_id));
create table Output (_id bigint generated by default as identity, value varchar(255) not null, primary key (_id));
create table Price (_id bigint generated by default as identity, currency varchar(255) not null, value decimal(19,2) not null, primary key (_id));
create table Product (_id bigint generated by default as identity, ageGroup varchar(255), availability varchar(255) not null, brand varchar(255) not null, condition varchar(255) not null, contentLanguage varchar(255) not null, delete boolean not null, description clob not null, gtin bigint, id varchar(255) not null, isGroupParent boolean, itemGroupId varchar(255), link binary(255) not null, mpn varchar(255) not null, quantity bigint not null, targetCountry varchar(255) not null, title varchar(255) not null, category__id bigint not null, price__id bigint not null, salePrice__id bigint not null, primary key (_id));
create table Product_CustomAttribute (Product__id bigint not null, customAttributes__id bigint not null);
create table Product_ImageLink (Product__id bigint not null, imageLinks__id bigint not null);
create table Product_Output (Product__id bigint not null, output__id bigint not null);
create table Root (_id bigint generated by default as identity, id varchar(255) not null, rights varchar(255) not null, subtitle varchar(255) not null, title varchar(255) not null, updated varchar(255) not null, author__id bigint, primary key (_id));
create table Root_Product (Root__id bigint not null, products__id bigint not null);
create table Rule (_id bigint generated by default as identity, fromDate timestamp not null, isActive boolean, marketplaceId varchar(255) not null, name varchar(255) not null, toDate timestamp not null, primary key (_id));
alter table AmazonFeedSubmission add constraint UK_2jmyvh74g6lxpa9as6q3c111y unique (feedSubmissionId);
alter table Brand add constraint UK_b14lvebnt5suo43obgxmgwaim unique (name);
alter table DataFeed add constraint UK_6m2xexd5gcme9hicei825if1r unique (root__id);
alter table Product add constraint UK_lmtrd4q1xa0iywe905vga4l13 unique (price__id);
alter table Product add constraint UK_33kiwbv9d6o915d55xsqf5i0l unique (salePrice__id);
alter table Product_CustomAttribute add constraint UK_hdfm9ucoxh9sub8h99cfgem8g unique (customAttributes__id);
alter table Product_ImageLink add constraint UK_h6936tjnjrwax3tklqga5ndbv unique (imageLinks__id);
alter table Product_Output add constraint UK_8tonfq5h9bno50ccuk521pmpr unique (output__id);
alter table Root_Product add constraint UK_3hjqnemu3bhjh6e7hflejytst unique (products__id);
alter table Brand_Rule add constraint FK3g317r71y6ns6ksb5ttycjhnb foreign key (rule__id) references Brand;
alter table Brand_Rule add constraint FK2cyy2dr55378rig4wa365la1c foreign key (brand__id) references Rule;
alter table DataFeed add constraint FKsyv0t6rxee6elb16ciuao651u foreign key (root__id) references Root;
alter table Product add constraint FKomtv0w2f3u1dp334psjj9fw0h foreign key (category__id) references Category;
alter table Product add constraint FKrksri2gqyal1d244xbco0hndo foreign key (price__id) references Price;
alter table Product add constraint FKfuw78qqevhsmqmj6sxsa8vrwd foreign key (salePrice__id) references Price;
alter table Product_CustomAttribute add constraint FKc3ns7qvh7qq5knfijluwenqwe foreign key (customAttributes__id) references CustomAttribute;
alter table Product_CustomAttribute add constraint FK5hegn2gksri8ll03iagtp8xsr foreign key (Product__id) references Product;
alter table Product_ImageLink add constraint FKomnevj4ix33oyygqmb47q7rol foreign key (imageLinks__id) references ImageLink;
alter table Product_ImageLink add constraint FK6shfb60uyh8s0muhdi69ab3im foreign key (Product__id) references Product;
alter table Product_Output add constraint FKghrt8wv9ny42tf9n2sr1lwl2k foreign key (output__id) references Output;
alter table Product_Output add constraint FK9b6j0fwns52xs6i8g2ye03gki foreign key (Product__id) references Product;
alter table Root add constraint FK6r8hhjpbiijvd401292invfda foreign key (author__id) references Author;
alter table Root_Product add constraint FKf2r4a8u2taqrb2ftnekx4qvj0 foreign key (products__id) references Product;
alter table Root_Product add constraint FKa2ascoysf6p9s0uwi3405mieh foreign key (Root__id) references Root;
