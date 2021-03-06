create table `AmazonFeedSubmission` (`_id` bigint not null auto_increment, `completedProcessingDate` datetime, `feedProcessingStatus` varchar(255) not null, `feedSubmissionId` varchar(255) not null, `feedSubmissionResult` varchar(255), `feedType` varchar(255) not null, `marketplaceId` varchar(255) not null, `messagesProcessed` integer, `messagesSuccessful` integer, `messagesWithError` integer, `messagesWithWarning` integer, `startedProcessingDate` datetime, `submittedDate` datetime not null, primary key (`_id`)) ENGINE=InnoDB;
create table `Author` (`_id` bigint not null auto_increment, `email` varchar(255) not null, `name` varchar(255) not null, `uri` varchar(255) not null, primary key (`_id`)) ENGINE=InnoDB;
create table `Brand` (`_id` bigint not null, `name` varchar(255) not null, primary key (`_id`)) ENGINE=InnoDB;
create table `Brand_Rule` (`brand__id` bigint not null, `rule__id` bigint not null) ENGINE=InnoDB;
create table `Category` (`_id` bigint not null auto_increment, `code` bigint not null, `name` varchar(255) not null, primary key (`_id`)) ENGINE=InnoDB;
create table `CustomAttribute` (`_id` bigint not null auto_increment, `name` varchar(255) not null, `unit` varchar(255), `value` longtext not null, primary key (`_id`)) ENGINE=InnoDB;
create table `DataFeed` (`_id` bigint not null auto_increment, `parseDateDateTime` datetime default CURRENT_TIMESTAMP, `root__id` bigint not null, primary key (`_id`)) ENGINE=InnoDB;
create table `ImageLink` (`_id` bigint not null auto_increment, `label` varchar(255) not null, `link` blob, primary key (`_id`)) ENGINE=InnoDB;
create table `Output` (`_id` bigint not null auto_increment, `value` varchar(255) not null, primary key (`_id`)) ENGINE=InnoDB;
create table `Price` (`_id` bigint not null auto_increment, `currency` varchar(255) not null, `value` decimal(19,2) not null, primary key (`_id`)) ENGINE=InnoDB;
create table `Product` (`_id` bigint not null auto_increment, `ageGroup` varchar(255), `availability` varchar(255) not null, `brand` varchar(255) not null, `condition` varchar(255) not null, `contentLanguage` varchar(255) not null, `delete` bit not null, `description` longtext not null, `gtin` bigint, `id` varchar(255) not null, `isGroupParent` bit, `itemGroupId` varchar(255), `link` tinyblob not null, `mpn` varchar(255) not null, `quantity` bigint not null, `targetCountry` varchar(255) not null, `title` varchar(255) not null, `category__id` bigint not null, `price__id` bigint not null, `salePrice__id` bigint not null, primary key (`_id`)) ENGINE=InnoDB;
create table `Product_CustomAttribute` (`Product__id` bigint not null, `customAttributes__id` bigint not null) ENGINE=InnoDB;
create table `Product_ImageLink` (`Product__id` bigint not null, `imageLinks__id` bigint not null) ENGINE=InnoDB;
create table `Product_Output` (`Product__id` bigint not null, `output__id` bigint not null) ENGINE=InnoDB;
create table `Root` (`_id` bigint not null auto_increment, `id` varchar(255) not null, `rights` varchar(255) not null, `subtitle` varchar(255) not null, `title` varchar(255) not null, `updated` varchar(255) not null, `author__id` bigint, primary key (`_id`)) ENGINE=InnoDB;
create table `Root_Product` (`Root__id` bigint not null, `products__id` bigint not null) ENGINE=InnoDB;
create table `Rule` (`_id` bigint not null auto_increment, `fromDate` datetime not null, `isActive` bit, `marketplaceId` varchar(255) not null, `name` varchar(255) not null, `toDate` datetime not null, primary key (`_id`)) ENGINE=InnoDB;
alter table `AmazonFeedSubmission` add constraint UK_2jmyvh74g6lxpa9as6q3c111y unique (`feedSubmissionId`);
alter table `Brand` add constraint UK_b14lvebnt5suo43obgxmgwaim unique (`name`);
alter table `DataFeed` add constraint UK_6m2xexd5gcme9hicei825if1r unique (`root__id`);
alter table `Product` add constraint UK_lmtrd4q1xa0iywe905vga4l13 unique (`price__id`);
alter table `Product` add constraint UK_33kiwbv9d6o915d55xsqf5i0l unique (`salePrice__id`);
alter table `Product_CustomAttribute` add constraint UK_hdfm9ucoxh9sub8h99cfgem8g unique (`customAttributes__id`);
alter table `Product_ImageLink` add constraint UK_h6936tjnjrwax3tklqga5ndbv unique (`imageLinks__id`);
alter table `Product_Output` add constraint UK_8tonfq5h9bno50ccuk521pmpr unique (`output__id`);
alter table `Root_Product` add constraint UK_3hjqnemu3bhjh6e7hflejytst unique (`products__id`);
alter table `Brand_Rule` add constraint `FK1sf9yib73cvy6oyr44yubf3rr` foreign key (`rule__id`) references `Brand` (`_id`);
alter table `Brand_Rule` add constraint `FK2kb4pvyk0dyyr42bfugaciohs` foreign key (`brand__id`) references `Rule` (`_id`);
alter table `DataFeed` add constraint `FKcig5tra3c497bh1ttje7r8kug` foreign key (`root__id`) references `Root` (`_id`);
alter table `Product` add constraint `FK48a1i0xbkt3jcx02o85nu9dkc` foreign key (`category__id`) references `Category` (`_id`);
alter table `Product` add constraint `FKhtc18ub0r1c7wqmaguu3gqvv3` foreign key (`price__id`) references `Price` (`_id`);
alter table `Product` add constraint `FKr76o3pvayty43nlwy314xb75y` foreign key (`salePrice__id`) references `Price` (`_id`);
alter table `Product_CustomAttribute` add constraint `FKm1xcjkp1itar8hpb8kv4r04d3` foreign key (`customAttributes__id`) references `CustomAttribute` (`_id`);
alter table `Product_CustomAttribute` add constraint `FKmq7xaqvovsvnr132ucq4bed9j` foreign key (`Product__id`) references `Product` (`_id`);
alter table `Product_ImageLink` add constraint `FK8ol8t3slcrwlsvtcff7jxab84` foreign key (`imageLinks__id`) references `ImageLink` (`_id`);
alter table `Product_ImageLink` add constraint `FKh2tq5bb74bb67uokj7xau6gr8` foreign key (`Product__id`) references `Product` (`_id`);
alter table `Product_Output` add constraint `FKoirkyutsaudpuja4uiv44a5aa` foreign key (`output__id`) references `Output` (`_id`);
alter table `Product_Output` add constraint `FK4d67cgx619vyroqksqu8epso4` foreign key (`Product__id`) references `Product` (`_id`);
alter table `Root` add constraint `FKrpktp3u2xvh86dxhq3rljvcjo` foreign key (`author__id`) references `Author` (`_id`);
alter table `Root_Product` add constraint `FK5x76f7vxqpre0liuulr9fstce` foreign key (`products__id`) references `Product` (`_id`);
alter table `Root_Product` add constraint `FKpmjl2f3s57vik0yq6hy385f2q` foreign key (`Root__id`) references `Root` (`_id`);
