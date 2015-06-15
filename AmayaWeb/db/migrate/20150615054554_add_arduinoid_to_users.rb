class AddArduinoidToUsers < ActiveRecord::Migration
  def change
    add_column :notifications, :arduino_id, :string
  end
end
