class AddUnicodeChineseToDrawing < ActiveRecord::Migration
  def change
  	add_column :drawings, :chinese, :string
  end
end
