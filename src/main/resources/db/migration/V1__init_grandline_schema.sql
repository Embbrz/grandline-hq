CREATE TABLE crews (
	id UUID PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	captain_id UUID,
	total_bounty BIGINT DEFAULT 0
);

CREATE TABLE devil_fruits (
	id UUID PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	fruit_type VARCHAR(50) NOT NULL,
	is_awakened BOOLEAN DEFAULT FALSE
);

CREATE TABLE pirates (
	id UUID PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	current_bounty BIGINT NOT NULL,
	
	status VARCHAR(50) NOT NULL,
	version BIGINT DEFAULT 0,
	crew_id UUID REFERENCES crews(id),
	devil_fruit_id UUID REFERENCES devil_fruits(id)
);

CREATE TABLE bounty_hunters (
	id UUID PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	bounty_claimed INT DEFAULT 0,
	total_bounty_cash BIGINT DEFAULT 0
	
);
CREATE TABLE bounty_claims (
	id UUID PRIMARY KEY,
	pirate_id UUID REFERENCES pirates(id),
	pirate_is_alive BOOLEAN DEFAULT true,
	bounty_hunter_id UUID REFERENCES bounty_hunters(id),
	reward_paid BIGINT NOT NULL,
	status VARCHAR(50) NOT NULL
);