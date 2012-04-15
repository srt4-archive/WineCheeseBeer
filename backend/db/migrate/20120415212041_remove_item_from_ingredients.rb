class RemoveItemFromIngredients < ActiveRecord::Migration
  def up
	remove_column :ingredients, :item
  end

  def down
  end
end
