class CreateDrawings < ActiveRecord::Migration
  def change
    create_table :drawings do |t|
      t.string :path
      t.references :user, index: true, foreign_key: true

      t.timestamps null: false
    end
  end
end
