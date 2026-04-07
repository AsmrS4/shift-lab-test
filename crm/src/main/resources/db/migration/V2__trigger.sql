CREATE OR REPLACE FUNCTION sellers_audit_before_trigger_function()
RETURNS TRIGGER AS $$
DECLARE
    v_operation_type VARCHAR(10);
BEGIN
    IF TG_OP = 'INSERT' THEN
        v_operation_type := 'INSERT';

        INSERT INTO sellers_audit (
            seller_id, prev_name, prev_contact_info, prev_status,
            new_name, new_contact_info, new_status,
            operation_type, modified_at
        ) VALUES (
            NEW.id, NULL, NULL, NULL,
            NEW.name, NEW.contact_info, NEW.is_active,
            v_operation_type, CURRENT_TIMESTAMP
        );

        RETURN NEW;

    ELSIF TG_OP = 'UPDATE' THEN
        IF OLD.name IS DISTINCT FROM NEW.name OR
           OLD.contact_info IS DISTINCT FROM NEW.contact_info OR
           OLD.is_active IS DISTINCT FROM NEW.is_active THEN

            v_operation_type := 'UPDATE';

            INSERT INTO sellers_audit (
                seller_id, prev_name, prev_contact_info, prev_status,
                new_name, new_contact_info, new_status,
                operation_type, modified_at
            ) VALUES (
                NEW.id, OLD.name, OLD.contact_info, OLD.is_active,
                NEW.name, NEW.contact_info, NEW.is_active,
                v_operation_type, CURRENT_TIMESTAMP
            );

            NEW.modified_at := CURRENT_TIMESTAMP;
        END IF;

        RETURN NEW;
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_sellers_audit_before
AFTER INSERT OR UPDATE ON sellers
FOR EACH ROW
EXECUTE FUNCTION sellers_audit_before_trigger_function();