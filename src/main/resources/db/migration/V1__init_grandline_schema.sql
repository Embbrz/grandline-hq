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

CREATE TABLE bounty_claims (
	id UUID PRIMARY KEY,
	pirate_id UUID REFERENCES pirates(id),
	hunter_id UUID NOT NULL,
	reward_paid BIGINT NOT NULL,
	status VARCHAR(50) NOT NULL
);