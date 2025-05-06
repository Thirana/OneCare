-- Insert Doctors
INSERT INTO doctor (id, name, specialization, email, phone, address)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'Dr. Alice Smith', 'Cardiology', 'alice.smith@example.com', '1234567890', '123 Heart Street'),
    ('22222222-2222-2222-2222-222222222222', 'Dr. Bob Johnson', 'Neurology', 'bob.johnson@example.com', '2345678901', '456 Brain Blvd'),
    ('33333333-3333-3333-3333-333333333333', 'Dr. Carol White', 'Dermatology', 'carol.white@example.com', '3456789012', '789 Skin Avenue');

-- Insert Availability (with doctor_id foreign keys)
INSERT INTO availability (id, date, start_time, end_time, is_booked, doctor_id)
VALUES
    ('aaaa1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '2025-05-10', '09:00:00', '12:00:00', FALSE, '11111111-1111-1111-1111-111111111111'),
    ('aaaa2222-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '2025-05-12', '14:00:00', '17:00:00', FALSE, '11111111-1111-1111-1111-111111111111'),
    ('bbbb1111-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '2025-05-11', '10:00:00', '13:00:00', FALSE, '22222222-2222-2222-2222-222222222222'),
    ('bbbb2222-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '2025-05-13', '15:00:00', '18:00:00', FALSE, '22222222-2222-2222-2222-222222222222');
