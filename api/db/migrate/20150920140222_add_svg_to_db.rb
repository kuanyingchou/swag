class AddSvgToDb < ActiveRecord::Migration
  def change
  	add_column :drawings, :svg, :text
  end
end
