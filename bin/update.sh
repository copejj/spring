FILE="sql/inc/updates.sql"

if [[ -f "$FILE" && -s "$FILE" ]]; then
    echo "The file '$FILE' exists, is a regular file, and is not empty."
	psql -U postgres -d postgres < "$FILE"
elif [[ -f "$FILE" && ! -s "$FILE" ]]; then
    echo "The file '$FILE' exists and is empty."
else
    echo "The file '$FILE' does not exist or is not a regular file."
fi