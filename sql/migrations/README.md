Migration Naming Convention

To ensure scripts are executed in the correct chronological order and are picked up by the automation engine, all files must follow this format:
	YYYYMMDD_[Number]_(ShortDescription).sql

Format Breakdown:
	YYYYMMDD: The 8-digit date the migration was created (e.g., 20231025).
	_: A literal underscore separator.
	[Number]: A sequential number (1, 2, 3...) to handle multiple migrations created on the same day.
	(ShortDescription): A brief, slug-style description of the change (e.g., add_user_index).
	.sql: The file extension.

Valid Examples:
	20231025_1_init_auth_tables.sql
	20231025_2_add_email_column.sql
	20231101_1_update_constraints.sql
Invalid Examples:
	update_v1.sql (Missing date prefix)
	2023-10-25-migration.sql (Uses dashes instead of underscores)
	10252023_1_fix.sql (Wrong date format)