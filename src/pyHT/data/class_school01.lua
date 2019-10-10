return {
  version = "1.1",
  luaversion = "5.1",
  tiledversion = "0.18.2",
  orientation = "isometric",
  renderorder = "left-down",
  width = 50,
  height = 20,
  tilewidth = 250,
  tileheight = 129,
  nextobjectid = 1,
  properties = {
    ["event_spotted"] = "pear monster",
    ["event_spotted_m"] = "Eeek! Whats that?!?",
    ["event_spotted_p"] = "prt_yukari_00.png",
    ["hint"] = "Some monsters resurrect after a few turns.",
    ["mapname"] = "class_school01.tmx",
    ["nextmap"] = "apartment1.tmx",
    ["obj"] = "You left your cellphone in the locker room.",
    ["planning_0"] = "I have to get my cell phone.",
    ["planning_0_p"] = "prt_player_00.png",
    ["planning_1"] = "It should be in the locker room on the second pillar, right side.",
    ["planning_1_p"] = "prt_player_00.png",
    ["planning_2"] = "Yukari: Ok lets hurry! I dont like dark places.",
    ["planning_2_p"] = "prt_yukari_00.png",
    ["req_goal"] = "yes"
  },
  tilesets = {
    {
      name = "walls_image",
      firstgid = 1,
      tilewidth = 250,
      tileheight = 512,
      spacing = 0,
      margin = 0,
      image = "walls250x512.png",
      imagewidth = 2000,
      imageheight = 2048,
      tileoffset = {
        x = 0,
        y = 0
      },
      properties = {},
      terrains = {},
      tilecount = 32,
      tiles = {}
    },
    {
      name = "actors_tiles",
      firstgid = 33,
      tilewidth = 218,
      tileheight = 312,
      spacing = 0,
      margin = 0,
      image = "actor_grid.png",
      imagewidth = 872,
      imageheight = 1248,
      tileoffset = {
        x = 0,
        y = 0
      },
      properties = {},
      terrains = {},
      tilecount = 16,
      tiles = {
        {
          id = 0,
          properties = {
            ["actor_name"] = "player",
            ["costume"] = "player00"
          }
        },
        {
          id = 1,
          properties = {
            ["actor_name"] = "Yukari"
          }
        },
        {
          id = 3,
          properties = {
            ["actor_name"] = "Ichi"
          }
        },
        {
          id = 8,
          properties = {
            ["actor_name"] = "pear monster"
          }
        },
        {
          id = 12,
          properties = {
            ["audio_trigger"] = "trapped_girl"
          }
        },
        {
          id = 13,
          properties = {
            ["event_exit"] = "exit",
            ["event_exit_m"] = "end",
            ["event_exit_p"] = "prt_player_00.png"
          }
        },
        {
          id = 14,
          properties = {
            ["event_goal"] = "yes",
            ["event_goal_graphic"] = "mission_cellphone.png",
            ["event_goal_m"] = "I got it! Lets get out of here.",
            ["event_goal_p"] = "prt_player_00.png"
          }
        },
        {
          id = 15,
          properties = {
            ["weapon_name"] = "knife"
          }
        }
      }
    },
    {
      name = "tiles250x129",
      firstgid = 49,
      tilewidth = 250,
      tileheight = 130,
      spacing = 0,
      margin = 0,
      image = "tiles250x129.png",
      imagewidth = 2000,
      imageheight = 520,
      tileoffset = {
        x = 0,
        y = 0
      },
      properties = {},
      terrains = {},
      tilecount = 32,
      tiles = {}
    },
    {
      name = "walls250x512_2",
      firstgid = 81,
      tilewidth = 250,
      tileheight = 512,
      spacing = 0,
      margin = 0,
      image = "walls250x512_2.png",
      imagewidth = 2000,
      imageheight = 2048,
      tileoffset = {
        x = 0,
        y = 0
      },
      properties = {},
      terrains = {},
      tilecount = 32,
      tiles = {}
    }
  },
  layers = {
    {
      type = "tilelayer",
      name = "background_layer",
      x = 0,
      y = 0,
      width = 50,
      height = 20,
      visible = true,
      opacity = 0.99,
      offsetx = 0,
      offsety = 0,
      properties = {},
      encoding = "base64",
      compression = "gzip",
      data = "H4sIAAAAAAAAC+3DQQ0AAAwDodNU/+ImYx9IWDVVVVX19QGnj3lvoA8AAA=="
    },
    {
      type = "tilelayer",
      name = "walls_layer",
      x = 0,
      y = 0,
      width = 50,
      height = 20,
      visible = true,
      opacity = 1,
      offsetx = 0,
      offsety = 0,
      properties = {},
      encoding = "base64",
      compression = "gzip",
      data = "H4sIAAAAAAAAC+1W7Q7DIAgkdlv7Zw+593+JzR8mlHEKFmeT7pJLqyhw8QMTEaUPF8bU2Yfsv+JGezxBThtjzd6aSyBuwQv0c0hfyTBnBHLclbUf4lv713zN1BHpa5YOCc96rLRfy6zhPiivkVhFO+u4zUiEvveBdz2kr7Occw+0fWXRge7rll0by+dE31cWLlSvcdIux7R0HAGvg4hlnISlVvE4Wh+qg16gem7Jwxsn2qf0ZdHB7+ayh5A/zYZ0RBPlVcDv5rL3NdRsSEfU+9Si4yw1X0PtvauNPRprFK6uI+qc96KnBtfqodf2p41vvUBXRKAPAAA="
    },
    {
      type = "tilelayer",
      name = "actors_layer",
      x = 0,
      y = 0,
      width = 50,
      height = 20,
      visible = true,
      opacity = 1,
      offsetx = 0,
      offsety = 0,
      properties = {},
      encoding = "base64",
      compression = "gzip",
      data = "H4sIAAAAAAAAC+3VMQoAIQxE0VxFsbHRvf/pVnvtAh/kP0g9M1UilOGjCxx0OL/C+VnaukGXSFDijR3bKzskcegfeTPpApIk1A8W6J/ZoA8AAA=="
    }
  }
}
