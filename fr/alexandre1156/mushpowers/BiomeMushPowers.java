package fr.alexandre1156.mushpowers;

import java.util.Random;

import fr.alexandre1156.mushpowers.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BiomeMushPowers extends Biome {

	public BiomeMushPowers() {
		super(new Biome.BiomeProperties("MushpowersBiome").func_185396_a().func_185398_c(0.125F)
				.func_185400_d(0.05F).func_185410_a(0.8f));
		this.field_82914_M.clear();
		this.field_76762_K.clear();
		this.field_76761_J.clear();
		this.field_76755_L.clear();
		this.field_76752_A = Blocks.field_150391_bh.func_176223_P();
		this.field_76760_I.field_76807_J = 2;
		this.field_76760_I.field_76800_F = 0;
		this.field_76760_I.field_76806_I = 0;
		this.field_76760_I.field_76804_C = 0;
		this.field_76760_I.field_189870_A = 0;
		this.field_76760_I.field_76802_A = 0;
		this.field_76760_I.field_76808_K = false;
		this.field_76760_I.field_76803_B = 0;
		this.field_76760_I.field_76799_E = 0;
		this.field_76760_I.field_76801_G = 0;
		this.field_76760_I.field_76805_H = 0;
		this.field_76760_I.field_76832_z = 0;
		this.field_76760_I.field_76833_y = 0;
		this.field_76760_I.field_76826_u = new WorldGenBigMushroom(Blocks.field_150419_aX);
		this.field_76760_I.field_76798_D = 10;
	}

	@SubscribeEvent
	public void onMushGen(DecorateBiomeEvent.Decorate e) {
		if (e.getType() == DecorateBiomeEvent.Decorate.EventType.SHROOM
				&& e.getWorld().func_180494_b(e.getPos()).func_185359_l().equals(this.func_185359_l())) {
			//System.out.println("CALLED");
			// e.setCanceled(true);
			e.setResult(Result.DENY);
			for (int l3 = 0; l3 < this.field_76760_I.field_76798_D; ++l3) {
				switch (e.getRand().nextInt(30)) {
				case 0:
					int i8 = e.getRand().nextInt(16) + 8;
					int l11 = e.getRand().nextInt(16) + 8;
					BlockPos blockpos2 = e.getWorld().func_175645_m(e.getPos().func_177982_a(i8, 0, l11));
					this.generateMushshroomPower(e.getWorld(), e.getRand(), blockpos2, CommonProxy.bushChickenMush);
					break;
				case 1:
					int i = e.getRand().nextInt(16) + 8;
					int l = e.getRand().nextInt(16) + 8;
					BlockPos blockpos = e.getWorld().func_175645_m(e.getPos().func_177982_a(i, 0, l));
					this.generateMushshroomPower(e.getWorld(), e.getRand(), blockpos, CommonProxy.bushCursedMush);
					break;
				case 2:
					int i1 = e.getRand().nextInt(16) + 8;
					int l1 = e.getRand().nextInt(16) + 8;
					BlockPos blockpos3 = e.getWorld().func_175645_m(e.getPos().func_177982_a(i1, 0, l1));
					this.generateMushshroomPower(e.getWorld(), e.getRand(), blockpos3, CommonProxy.bushElectricshroom);
					break;
				case 3:
					int i2 = e.getRand().nextInt(16) + 8;
					int l2 = e.getRand().nextInt(16) + 8;
					BlockPos blockpos4 = e.getWorld().func_175645_m(e.getPos().func_177982_a(i2, 0, l2));
					this.generateMushshroomPower(e.getWorld(), e.getRand(), blockpos4, CommonProxy.bushFlyshroom);
					break;
				case 4:
					int i3 = e.getRand().nextInt(16) + 8;
					int l12 = e.getRand().nextInt(16) + 8;
					BlockPos blockpos5 = e.getWorld().func_175645_m(e.getPos().func_177982_a(i3, 0, l12));
					this.generateMushshroomPower(e.getWorld(), e.getRand(), blockpos5, CommonProxy.bushGhostshroom);
					break;
				case 5:
					int i4 = e.getRand().nextInt(16) + 8;
					int l4 = e.getRand().nextInt(16) + 8;
					BlockPos blockpos6 = e.getWorld().func_175645_m(e.getPos().func_177982_a(i4, 0, l4));
					this.generateMushshroomPower(e.getWorld(), e.getRand(), blockpos6, CommonProxy.bushHostileshroom);
					break;
				case 6:
					int i5 = e.getRand().nextInt(16) + 8;
					int l5 = e.getRand().nextInt(16) + 8;
					BlockPos blockpos7 = e.getWorld().func_175645_m(e.getPos().func_177982_a(i5, 0, l5));
					this.generateMushshroomPower(e.getWorld(), e.getRand(), blockpos7, CommonProxy.bushLowershroom);
					break;
				case 7:
					int i6 = e.getRand().nextInt(16) + 8;
					int l6 = e.getRand().nextInt(16) + 8;
					BlockPos blockpos8 = e.getWorld().func_175645_m(e.getPos().func_177982_a(i6, 0, l6));
					this.generateMushshroomPower(e.getWorld(), e.getRand(), blockpos8, CommonProxy.bushPizzashroom);
					break;
				case 8:
					int i7 = e.getRand().nextInt(16) + 8;
					int l7 = e.getRand().nextInt(16) + 8;
					BlockPos blockpos9 = e.getWorld().func_175645_m(e.getPos().func_177982_a(i7, 0, l7));
					this.generateMushshroomPower(e.getWorld(), e.getRand(), blockpos9, CommonProxy.bushRegenshroom);
					break;
				case 9:
					int i9 = e.getRand().nextInt(16) + 8;
					int l9 = e.getRand().nextInt(16) + 8;
					BlockPos blockpos10 = e.getWorld().func_175645_m(e.getPos().func_177982_a(i9, 0, l9));
					this.generateMushshroomPower(e.getWorld(), e.getRand(), blockpos10, CommonProxy.bushShieldshroom);
					break;
				case 10:
					int i10 = e.getRand().nextInt(16) + 8;
					int l10 = e.getRand().nextInt(16) + 8;
					BlockPos blockpos11 = e.getWorld().func_175645_m(e.getPos().func_177982_a(i10, 0, l10));
					this.generateMushshroomPower(e.getWorld(), e.getRand(), blockpos11, CommonProxy.bushSquidMush);
					break;
				case 11:
					int i13 = e.getRand().nextInt(16) + 8;
					int l13 = e.getRand().nextInt(16) + 8;
					BlockPos blockpos12 = e.getWorld().func_175645_m(e.getPos().func_177982_a(i13, 0, l13));
					this.generateMushshroomPower(e.getWorld(), e.getRand(), blockpos12,
							CommonProxy.bushZombieawayShroom);
					break;
				}
			}
		} else if (e.getType() == DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM
				&& e.getWorld().func_180494_b(e.getPos()).func_185359_l().equals(this.func_185359_l())) {
			e.setResult(Result.DENY);
			for (int k2 = 0; k2 < this.field_76760_I.field_76807_J; ++k2) {
				int l6 = e.getRand().nextInt(16) + 8;
				int k10 = e.getRand().nextInt(16) + 8;
				this.generateRedBigMushroom(e.getWorld(), e.getRand(),
						e.getWorld().func_175645_m(e.getPos().func_177982_a(l6, 0, k10)));
			}
		}
	}

	private void generateMushshroomPower(World world, Random rand, BlockPos pos, BlockBush mush) {
		for (int i = 0; i < 64; ++i) {
			BlockPos blockpos = pos.func_177982_a(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4),
					rand.nextInt(8) - rand.nextInt(8));
			if (world.func_175623_d(blockpos) && (!world.field_73011_w.func_177495_o() || blockpos.func_177956_o() < world.func_72800_K() - 1)
					&& mush.func_180671_f(world, blockpos, mush.func_176223_P())) {
				world.func_180501_a(blockpos, mush.func_176223_P(), 2);
			}
		}
	}

	private boolean generateRedBigMushroom(World worldIn, Random rand, BlockPos position) {
		Block block = Blocks.field_150419_aX;

		int i = rand.nextInt(3) + 4;

		if (rand.nextInt(12) == 0) {
			i *= 2;
		}

		boolean flag = true;

		if (position.func_177956_o() >= 1 && position.func_177956_o() + i + 1 < 256) {
			for (int j = position.func_177956_o(); j <= position.func_177956_o() + 1 + i; ++j) {
				int k = 3;

				if (j <= position.func_177956_o() + 3) {
					k = 0;
				}

				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

				for (int l = position.func_177958_n() - k; l <= position.func_177958_n() + k && flag; ++l) {
					for (int i1 = position.func_177952_p() - k; i1 <= position.func_177952_p() + k && flag; ++i1) {
						if (j >= 0 && j < 256) {
							IBlockState state = worldIn.func_180495_p(blockpos$mutableblockpos.func_181079_c(l, j, i1));

							if (!state.func_177230_c().isAir(state, worldIn, blockpos$mutableblockpos)
									&& !state.func_177230_c().isLeaves(state, worldIn, blockpos$mutableblockpos)) {
								flag = false;
							}
						} else {
							flag = false;
						}
					}
				}
			}

			if (!flag) {
				return false;
			} else {
				Block block1 = worldIn.func_180495_p(position.func_177977_b()).func_177230_c();

				if (block1 != Blocks.field_150346_d && block1 != Blocks.field_150349_c && block1 != Blocks.field_150391_bh) {
					return false;
				} else {
					int k2 = position.func_177956_o() + i;

					if (block == Blocks.field_150419_aX) {
						k2 = position.func_177956_o() + i - 3;
					}

					for (int l2 = k2; l2 <= position.func_177956_o() + i; ++l2) {
						int j3 = 1;

						if (l2 < position.func_177956_o() + i) {
							++j3;
						}

						if (block == Blocks.field_150420_aW) {
							j3 = 3;
						}

						int k3 = position.func_177958_n() - j3;
						int l3 = position.func_177958_n() + j3;
						int j1 = position.func_177952_p() - j3;
						int k1 = position.func_177952_p() + j3;

						for (int l1 = k3; l1 <= l3; ++l1) {
							for (int i2 = j1; i2 <= k1; ++i2) {
								int j2 = 5;

								if (l1 == k3) {
									--j2;
								} else if (l1 == l3) {
									++j2;
								}

								if (i2 == j1) {
									j2 -= 3;
								} else if (i2 == k1) {
									j2 += 3;
								}

								BlockHugeMushroom.EnumType blockhugemushroom$enumtype = BlockHugeMushroom.EnumType
										.func_176895_a(j2);

								if (block == Blocks.field_150420_aW || l2 < position.func_177956_o() + i) {
									if ((l1 == k3 || l1 == l3) && (i2 == j1 || i2 == k1)) {
										continue;
									}

									if (l1 == position.func_177958_n() - (j3 - 1) && i2 == j1) {
										blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_WEST;
									}

									if (l1 == k3 && i2 == position.func_177952_p() - (j3 - 1)) {
										blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_WEST;
									}

									if (l1 == position.func_177958_n() + (j3 - 1) && i2 == j1) {
										blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_EAST;
									}

									if (l1 == l3 && i2 == position.func_177952_p() - (j3 - 1)) {
										blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_EAST;
									}

									if (l1 == position.func_177958_n() - (j3 - 1) && i2 == k1) {
										blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_WEST;
									}

									if (l1 == k3 && i2 == position.func_177952_p() + (j3 - 1)) {
										blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_WEST;
									}

									if (l1 == position.func_177958_n() + (j3 - 1) && i2 == k1) {
										blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_EAST;
									}

									if (l1 == l3 && i2 == position.func_177952_p() + (j3 - 1)) {
										blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_EAST;
									}
								}

								if (blockhugemushroom$enumtype == BlockHugeMushroom.EnumType.CENTER
										&& l2 < position.func_177956_o() + i) {
									blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.ALL_INSIDE;
								}

								if (position.func_177956_o() >= position.func_177956_o() + i - 1
										|| blockhugemushroom$enumtype != BlockHugeMushroom.EnumType.ALL_INSIDE) {
									BlockPos blockpos = new BlockPos(l1, l2, i2);
									IBlockState state = worldIn.func_180495_p(blockpos);

									if (state.func_177230_c().canBeReplacedByLeaves(state, worldIn, blockpos)) {
										worldIn.func_180501_a(blockpos, block.func_176223_P()
												.func_177226_a(BlockHugeMushroom.field_176380_a, blockhugemushroom$enumtype), 3);
									}
								}
							}
						}
					}

					for (int i3 = 0; i3 < i; ++i3) {
						IBlockState iblockstate = worldIn.func_180495_p(position.func_177981_b(i3));

						if (iblockstate.func_177230_c().canBeReplacedByLeaves(iblockstate, worldIn, position.func_177981_b(i3))) {
							worldIn.func_180501_a(position.func_177981_b(i3), block.func_176223_P()
									.func_177226_a(BlockHugeMushroom.field_176380_a, BlockHugeMushroom.EnumType.STEM), 3);
						}
					}

					return true;
				}
			}
		} else {
			return false;
		}
	}

	@Override
	public void func_180622_a(World world, Random rand, ChunkPrimer primer, int x, int z, double noise) {
		this.biomeTerrain(world, rand, primer, x, z, noise);
	}

	private void biomeTerrain(World world, Random rand, ChunkPrimer primer, int x, int z, double noise) {
		boolean flag = true;
		IBlockState iblockstate = this.field_76752_A;
		IBlockState iblockstate1 = this.field_76753_B;
		int k = -1;
		int l = (int) (noise / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		int i1 = x & 15;
		int j1 = z & 15;

		for (int k1 = 255; k1 >= 0; --k1) {
			if (k1 <= 1) {
				primer.func_177855_a(j1, k1, i1, Blocks.field_150357_h.func_176223_P());
			} else {
				IBlockState iblockstate2 = primer.func_177856_a(j1, k1, i1);
				if (iblockstate2.func_177230_c().func_149688_o(iblockstate2) == Material.field_151579_a) {
					k = -1;
				} else if (iblockstate2.func_177230_c() == Blocks.field_150348_b) {
					if (k == -1) {
						if (l <= 0) {
							iblockstate = null;
							iblockstate1 = Blocks.field_150348_b.func_176223_P();
						} else if (k1 >= 59 && k1 <= 64) {
							iblockstate = this.field_76752_A;
							iblockstate1 = this.field_76753_B;
						}

						if (k1 < 63 && (iblockstate == null
								|| iblockstate.func_177230_c().func_149688_o(iblockstate) == Material.field_151579_a)) {
							if (this.func_180626_a(new BlockPos(x, k1, z)) < 0.15F) {
								iblockstate = Blocks.field_150432_aD.func_176223_P();
							} else {
								iblockstate = Blocks.field_150355_j.func_176223_P();
							}
						}

						k = l;

						if (k1 >= 62) {
							primer.func_177855_a(j1, k1, i1, iblockstate);
						} else if (k1 < 56 - l) {
							iblockstate = null;
							iblockstate1 = Blocks.field_150348_b.func_176223_P();
							primer.func_177855_a(j1, k1, i1, Blocks.field_150351_n.func_176223_P());
						} else {
							primer.func_177855_a(j1, k1, i1, iblockstate1);
						}
					} else if (k > 0) {
						--k;
						primer.func_177855_a(j1, k1, i1, iblockstate1);
					}
				}
			}
		}
	}

}
