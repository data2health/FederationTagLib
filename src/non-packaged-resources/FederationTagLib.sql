CREATE SCHEMA FederationTagLib;
CREATE SEQUENCE FederationTagLib.seqnum;
CREATE DOMAIN federation.image AS BYTEA;
CREATE DOMAIN federation.document AS BYTEA;
CREATE DOMAIN federation.trinary AS VARCHAR(5);
CREATE TABLE federation.outbound_query (
       qid INT NOT NULL
     , query_string TEXT
     , query_date TIMESTAMP
     , PRIMARY KEY (qid)
);

CREATE TABLE federation.inbound_query (
       qid INT NOT NULL
     , query_string TEXT
     , query_date TIMESTAMP
     , ip_address TEXT
     , PRIMARY KEY (qid)
);

CREATE TABLE federation.inbound_search (
       sid INT NOT NULL
     , search_string TEXT
     , search_date TIMESTAMP
     , ip_address TEXT
     , PRIMARY KEY (sid)
);

CREATE TABLE federation.site (
       sid INT NOT NULL
     , name TEXT
     , bootstrap_url TEXT
     , aggregate_query_url TEXT
     , last_validation TIMESTAMP
     , ip_address TEXT
     , PRIMARY KEY (sid)
);

CREATE TABLE federation.response (
       sid INT NOT NULL
     , qid INT NOT NULL
     , request_date TIMESTAMP
     , response_date TIMESTAMP
     , hit_count INT
     , population_type TEXT
     , preview_url TEXT
     , results_url TEXT
     , click_date TIMESTAMP
     , PRIMARY KEY (sid, qid)
     , CONSTRAINT FK_click_through_1 FOREIGN KEY (sid)
                  REFERENCES federation.site (sid)
     , CONSTRAINT FK_click_through_2 FOREIGN KEY (qid)
                  REFERENCES federation.outbound_query (qid)
);

