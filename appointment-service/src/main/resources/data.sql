-- Insert sample appointments
INSERT INTO appointment (id, patient_id, doctor_id, date, start_time, end_time, notes, status)
VALUES
    ('aaaaaaaa-1111-2222-3333-444444444444',
     '99999999-9999-9999-9999-999999999999', -- Patient ID from patient service
     '11111111-1111-1111-1111-111111111111', -- Doctor ID from doctor service
     '2025-05-10',
     '09:00:00',
     '10:00:00',
     'Regular checkup',
     'SCHEDULED'),

    ('bbbbbbbb-2222-3333-4444-555555555555',
     '88888888-8888-8888-8888-888888888888', -- Different patient
     '11111111-1111-1111-1111-111111111111', -- Same doctor
     '2025-05-10',
     '10:30:00',
     '11:30:00',
     'Follow-up appointment',
     'SCHEDULED'),

    ('cccccccc-3333-4444-5555-666666666666',
     '99999999-9999-9999-9999-999999999999', -- First patient
     '22222222-2222-2222-2222-222222222222', -- Different doctor
     '2025-05-11',
     '14:00:00',
     '15:00:00',
     'Specialist consultation',
     'SCHEDULED'),

    ('dddddddd-4444-5555-6666-777777777777',
     '77777777-7777-7777-7777-777777777777', -- Another patient
     '11111111-1111-1111-1111-111111111111', -- First doctor
     '2025-05-10',
     '13:00:00',
     '14:00:00',
     'Urgent consultation',
     'CANCELLED');
