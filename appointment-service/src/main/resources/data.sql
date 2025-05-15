-- Insert sample appointments
INSERT INTO appointment (id, patient_id, doctor_id, availability_id, notes)
VALUES
    ('aaaaaaaa-1111-2222-3333-444444444444',
     '99999999-9999-9999-9999-999999999999', -- Patient ID from patient service
     'dddd1111-dddd-dddd-dddd-dddddddddddd', -- Doctor ID from doctor service
     'aaaa1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', -- Availability ID from doctor service
     'Regular checkup'),

    ('bbbbbbbb-2222-3333-4444-555555555555',
     '88888888-8888-8888-8888-888888888888', -- Different patient
     'dddd1111-dddd-dddd-dddd-dddddddddddd', -- Same doctor
     'aaaa2222-aaaa-aaaa-aaaa-aaaaaaaaaaaa', -- Different availability slot
     'Follow-up appointment'),

    ('cccccccc-3333-4444-5555-666666666666',
     '99999999-9999-9999-9999-999999999999', -- First patient
     'dddd2222-dddd-dddd-dddd-dddddddddddd', -- Different doctor
     'bbbb1111-bbbb-bbbb-bbbb-bbbbbbbbbbbb', -- Another availability slot
     'Specialist consultation');
