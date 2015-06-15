class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users do |t|
      t.string :mail
      t.string :password
      t.string :arduino_id
      
      t.timestamps null: false
    end
  end
end
