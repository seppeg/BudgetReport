ALTER TABLE work_order ADD CONSTRAINT pk_work_order PRIMARY KEY (id);
ALTER TABLE day_booking ADD COLUMN work_order_id UUID NOT NULL;
UPDATE day_booking SET work_order_id = (SELECT w.id FROM work_order w WHERE w.work_order = work_order);
ALTER TABLE day_booking ADD CONSTRAINT fk_day_booking_work_order FOREIGN KEY (work_order_id) REFERENCES work_order(id);