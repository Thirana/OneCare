-- Drop table if it already exists (optional for dev)
DROP TABLE IF EXISTS appointment;

-- Create Appointment table
CREATE TABLE appointment (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    doctor_id UUID NOT NULL,
    availability_id UUID NOT NULL,
    notes TEXT
);

-- Insert sample appointments
INSERT INTO appointment (id, patient_id, doctor_id, availability_id, notes)
VALUES
    ('aaaaaaaa-1111-2222-3333-444444444444',
     '99999999-9999-9999-9999-999999999999',
     '11111111-1111-1111-1111-111111111111', -- Dr. Alice Smith
     'aaaa1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', -- Availability on 2025-05-10
     'Regular checkup'),

    ('bbbbbbbb-2222-3333-4444-555555555555',
     '88888888-8888-8888-8888-888888888888',
     '11111111-1111-1111-1111-111111111111',
     'aaaa2222-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
     'Follow-up appointment'),

    ('cccccccc-3333-4444-5555-666666666666',
     '99999999-9999-9999-9999-999999999999',
     '22222222-2222-2222-2222-222222222222', -- Dr. Bob Johnson
     'bbbb1111-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
     'Specialist consultation');
