class CreateNotifications < ActiveRecord::Migration
  def change
    create_table :notifications do |t|
      t.datetime :time

      t.timestamps null: false
    end
  end
end
