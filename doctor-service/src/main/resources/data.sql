-- Create Doctor table
CREATE TABLE IF NOT EXISTS doctor (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL,
    address TEXT NOT NULL
);

-- Create Availability table
CREATE TABLE IF NOT EXISTS availability (
    id UUID PRIMARY KEY,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    is_booked BOOLEAN NOT NULL DEFAULT FALSE,
    doctor_id UUID NOT NULL,
    CONSTRAINT fk_doctor
        FOREIGN KEY (doctor_id)
        REFERENCES doctor (id)
        ON DELETE CASCADE
);

-- Insert Doctors
INSERT INTO doctor (id, name, specialization, email, phone, address)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'Dr. Alice Smith', 'Cardiology', 'alice.smith@example.com', '1234567890', '123 Heart Street'),
    ('22222222-2222-2222-2222-222222222222', 'Dr. Bob Johnson', 'Neurology', 'bob.johnson@example.com', '2345678901', '456 Brain Blvd'),
    ('33333333-3333-3333-3333-333333333333', 'Dr. Carol White', 'Dermatology', 'carol.white@example.com', '3456789012', '789 Skin Avenue');

-- Insert Availability
INSERT INTO availability (id, date, start_time, end_time, is_booked, doctor_id)
VALUES
    ('aaaa1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '2025-05-10', '09:00:00', '12:00:00', FALSE, '11111111-1111-1111-1111-111111111111'),
    ('aaaa2222-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '2025-05-12', '14:00:00', '17:00:00', FALSE, '11111111-1111-1111-1111-111111111111'),
    ('bbbb1111-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '2025-05-11', '10:00:00', '13:00:00', FALSE, '22222222-2222-2222-2222-222222222222'),
    ('bbbb2222-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '2025-05-13', '15:00:00', '18:00:00', FALSE, '22222222-2222-2222-2222-222222222222');
